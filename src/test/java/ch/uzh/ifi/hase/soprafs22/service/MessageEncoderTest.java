package ch.uzh.ifi.hase.soprafs22.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

public class MessageEncoderTest {

    private MessageEncoder messageEncoder;

    @BeforeEach
    public void setup() {
    messageEncoder = new MessageEncoder();
    }

    @Test
    public void decodeMessageSuccess() throws EncodeException {
        String testString = "test";

        String decodedTestString = "s";
        
        decodedTestString = messageEncoder.encode("test");
        
        assertEquals(testString, decodedTestString);
    }

}

