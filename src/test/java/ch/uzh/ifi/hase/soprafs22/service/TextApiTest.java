package ch.uzh.ifi.hase.soprafs22.service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextApiTest {

    @Test
    public void messageWithInsult() {
        // given -> a first user has already been created
        String expected = "This comment contained inappropriate language (insult) and will not be displayed.";
        String actual = TextApi.moderateMessage("bitch");
        assertEquals(expected, actual);
    }

    @Test
    public void messageWithoutInsult() {
        // given -> a first user has already been created
        String expected = "Message without any inappropriate language";
        String actual = TextApi.moderateMessage("Message without any inappropriate language");
        assertEquals(expected, actual);
    }


}