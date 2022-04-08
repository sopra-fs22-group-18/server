package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

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


@Component
@ServerEndpoint(value = "/websocket/{username}/{sessionid}", 
                encoders = MessageEncoder.class,
                 decoders = MessageDecoder.class)
public class Socket {
    private Session session;
    //private ChatUser chatUser;
    public static Set<ChatUser> chatListeners = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("sessionid") Long sessionId) {  
        ChatUser chatUser = new ChatUser();
        this.session = session;
        chatUser.socket = this;
        chatUser.name = username;
        chatUser.sessionId = sessionId;

        chatListeners.add(chatUser);
        broadcast("Welcome to the session " + sessionId + ", " + username, sessionId);
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(String message, @PathParam("sessionid") Long sessionId) {
        broadcast(message, sessionId);
    }

    @OnClose
    public void onClose(Session session) {
        //chatListeners.remove(chatUser);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        //Error
    }

    public static void broadcast(String message, long sessionId) {
        // good for now but not scalable I guess
        for (ChatUser chatListener: chatListeners) {
            if (chatListener.sessionId == sessionId) {
                chatListener.socket.sendMessage(message);
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