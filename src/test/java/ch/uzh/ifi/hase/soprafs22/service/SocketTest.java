package ch.uzh.ifi.hase.soprafs22.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import ch.uzh.ifi.hase.soprafs22.entity.ChatUser;
import ch.uzh.ifi.hase.soprafs22.entity.Message;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

public class SocketTest {

    @Mock
    private Socket socket;

    private Iterator<ChatUser> chatIterator;
    private ChatUser chatUser;
    private Session session;

    @BeforeEach
    public void setup() {
        chatUser.setName("testName");
        chatUser.setSessionId(1L);
        chatUser.setUserId(1L);
        this.session = session;
        chatUser.setSocket(socket);

        chatIterator = mock(Iterator.class);
        when(chatIterator.hasNext()).thenReturn(true, false);
        when(chatIterator.next()).thenReturn(chatUser);

    }
    /* I have no idea how to test websockets
    @Test
    public void testOnClose() throws IOException {
        socket.onClose(session, chatUser.getUserId());

        assertEquals(chatIterator.next(), false); 
    }  */
}

