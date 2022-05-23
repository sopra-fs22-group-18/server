package ch.uzh.ifi.hase.soprafs22.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.uzh.ifi.hase.soprafs22.entity.Message;

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
        Message message = new Message();

        message.setContent("test content");
        message.setFrom("test from");

        Message decodedTestMessage = new Message();
        
        try {
            decodedTestMessage = messageDecoder.decode("{\"from\":\"test from\",\"content\":\"test content\"}");
        } catch (DecodeException e) {
            e.printStackTrace();
        }
        
        assertEquals(message.getContent(), decodedTestMessage.getContent());
        assertEquals(message.getFrom(), decodedTestMessage.getFrom());
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
