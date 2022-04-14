package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.ChatUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
@ServerEndpoint(value = "/websocket/{username}/{sessionId}", 
                encoders = MessageEncoder.class,
                 decoders = MessageDecoder.class)
public class Socket {
    private Session session;
    public static Set<ChatUser> chatListeners = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("sessionId") Long sessionId) {  
        ChatUser chatUser = new ChatUser();
        this.session = session;
        chatUser.setSocket(this);
        chatUser.setName(username);
        chatUser.setSessionId(sessionId);

        chatListeners.add(chatUser);
        broadcast("Welcome to the session " + sessionId + ", " + username, sessionId);
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(String message, @PathParam("sessionId") Long sessionId) {
        broadcast(message, sessionId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        //chatListeners.remove(chatUser);
        //bandaid fix for now =)
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.getName() == username) {
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