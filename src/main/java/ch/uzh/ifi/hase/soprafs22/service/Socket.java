package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.ChatUser;
import ch.uzh.ifi.hase.soprafs22.entity.Message;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

// should add rooms instead of session id if enough time

@Component
@ServerEndpoint(value = "/websocket/{userId}/{sessionId}", 
                encoders = MessageEncoder.class,
                decoders = MessageDecoder.class)
public class Socket {
    private Session session;
    private static Set<ChatUser> chatListeners = new CopyOnWriteArraySet<>();

    // add in Service so that they can be used by the socket. Autowired with Spring framework below.
    private static CommentService commentService;
    private static UserService userService;
    private static TextApi textApi;

    @Autowired
    public void setCommentService(CommentService commentService) {
        Socket.commentService = commentService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        Socket.userService = userService;
    }

    @Autowired
    public void setTextApi(TextApi textApi) {
        Socket.textApi = textApi;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId, @PathParam("sessionId") Long sessionId) {  
        ChatUser chatUser = new ChatUser();
        this.session = session;
        chatUser.setSocket(this);
        chatUser.setUserId(userId);
        chatUser.setSessionId(sessionId);

        // find user in the userService to access further user attributes
        User user = userService.getUser(userId);
        chatUser.setName(user.getUsername());

        chatListeners.add(chatUser);

        Message message = new Message();
        message.setFrom("Server");
        message.setContent("Welcome " + chatUser.getName() + " to session " + sessionId);
        broadcast(message, sessionId);
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(Message message, @PathParam("userId") Long userId, @PathParam("sessionId") Long sessionId) {
        String moderatedContent = textApi.moderateMessage(message.getContent());
        message.setContent(moderatedContent);
        commentService.createCommentFromSession(message.getContent(), userId, sessionId);
        broadcast(message, sessionId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        // bandaid fix for now (list.stream().filter can't cast optional)
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getUserId().equals(userId)) {
                chatListeners.remove(chatListener);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //Error
    }

    private static void broadcast(Message message, Long sessionId) {
        // good for now but not scalable I guess, paths or rooms would be an upgrade
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getSessionId().equals(sessionId)) {
                chatListener.getSocket().sendMessage(message);
            }
        }
    }

    private void sendMessage(Message message) {
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (EncodeException | IOException e) {
            System.out.println(e);
        }
    }

    //
    public void closeSession(Long sessionId, String winner) throws IOException {
        Message message = new Message();
        message.setFrom("Server");
        message.setContent("User : " + winner + " won, session closes");
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getSessionId().equals(sessionId)) {
                chatListener.getSocket().sendMessage(message);
                chatListeners.remove(chatListener);
                chatListener.getSocket().session.close();
                chatListener = null;
            }
        }
    }

}