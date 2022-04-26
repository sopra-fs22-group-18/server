package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.ChatUser;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
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

// should add rooms instead of sessionid if enough time

@Component
@ServerEndpoint(value = "/websocket/{userId}/{sessionId}", 
                encoders = MessageEncoder.class,
                decoders = MessageDecoder.class)
public class Socket {
    private Session session;
    public static Set<ChatUser> chatListeners = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId, @PathParam("sessionId") Long sessionId) {  
        ChatUser chatUser = new ChatUser();
        this.session = session;
        chatUser.setSocket(this);
        chatUser.setUserId(userId);
        chatUser.setSessionId(sessionId);
        // can't get Username without closing the connection instantly 

        chatListeners.add(chatUser);

        Message message = new Message();
        message.setFrom("Server");
        message.setContent("Welcome " + userId + " to session " + sessionId);
        broadcast(message, sessionId);
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(Message message, @PathParam("userId") Long userId, @PathParam("sessionId") Long sessionId) {
        broadcast(message, sessionId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        //chatListeners.remove(chatUser);
        //bandaid fix for now (list.stream().filter can't cast optional)
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getUserId() == userId) {
                chatListeners.remove(chatListener);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //Error
    }

    public static void broadcast(Message message, Long sessionId) {
        // good for now but not scalable I guess
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getSessionId() == sessionId) {
                chatListener.getSocket().sendMessage(message);
            }
        }
    }

    private void sendMessage(Message message) {
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (EncodeException | IOException e) {
            System.out.println(e);;
        }
    }

    //
    public void closeSession(Long sessionId, String winner) throws IOException {
        Message message = new Message();
        message.setFrom("Server");
        message.setContent("User : " + winner + " won, session closes");
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getSessionId() == sessionId) {
                chatListener.getSocket().sendMessage(message);
                chatListeners.remove(chatListener);
                chatListener.getSocket().session.close();
                chatListener = null;
            }
        }
    }
}