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
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

public class SocketTest {

    @Mock
    private Socket socket;

    @BeforeEach
    public void setup() {

    }

    /*
    @Test
    public void testCloseSession() throws IOException {
        Mockito.doNothing().when(socket).closeSession(Mockito.anyLong(), Mockito.any());

    } */
}

