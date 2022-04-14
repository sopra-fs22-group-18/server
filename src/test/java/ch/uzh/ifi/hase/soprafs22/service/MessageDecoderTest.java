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

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */

public class MessageDecoderTest {

    private MessageDecoder messageDecoder;

    @BeforeEach
    public void setup() {
    messageDecoder = new MessageDecoder();
    }

    @Test
    public void decodeMessageSuccess() {
        String testString = "test";

        String decodedTestString = "s";
        
        try {
            decodedTestString = messageDecoder.decode("test");
        } catch (DecodeException e) {
            e.printStackTrace();
        }
        
        assertEquals(testString, decodedTestString);
    }

    @Test
    public void decodeMessageStringEmpty() {
        String testString = null;

        assertFalse(messageDecoder.willDecode(testString));
    }

    @Test
    public void decodeMessageStringNotEmpty() {
        String testString = "test";

        assertTrue(messageDecoder.willDecode(testString));
    }
}

