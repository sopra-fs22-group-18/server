package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.ChatUser;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

// should add rooms instead of sessionId if enough time
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
        broadcast("Welcome to session " + sessionId + " user " + userId, sessionId);
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(String message, @PathParam("userId") Long userId, @PathParam("sessionId") Long sessionId) {
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

    public static void broadcast(String message, long sessionId) {
        // good for now but not scalable I guess
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getSessionId() == sessionId) {
                chatListener.getSocket().sendMessage(message);
            }
        }
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println(e);;
        }
    }
}