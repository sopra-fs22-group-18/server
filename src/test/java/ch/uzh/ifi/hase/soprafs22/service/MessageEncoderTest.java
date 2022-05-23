package ch.uzh.ifi.hase.soprafs22.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.uzh.ifi.hase.soprafs22.entity.Message;

import static org.junit.jupiter.api.Assertions.*;

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
        Message message = new Message();

        message.setContent("test content");
        message.setFrom("test from");
        
        String testString = "{\"from\":\"test from\",\"content\":\"test content\"}";

        String encodedTestString = messageEncoder.encode(message);
        
        assertEquals(testString, encodedTestString);
    }

}
