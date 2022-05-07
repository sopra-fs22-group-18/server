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


    @Test
    public void text_api_sexual() {
        // given -> a first user has already been created
        String expected="sexual";
        String actual=TextApi.checkComment("boobs");
        assertEquals(expected, actual);
    }
    @Test
    public void text_api_grawlix() {
        // given -> a first user has already been created
        String expected="grawlix";
        String actual=TextApi.checkComment("$#!t");
        assertEquals(expected, actual);
    }
    @Test
    public void text_api_discriminatory() {
        // given -> a first user has already been created
        String expected="discriminatory";
        String actual=TextApi.checkComment("chink");
        assertEquals(expected, actual);
    }
    @Test
    public void text_api_inappropriate() {
        // given -> a first user has already been created
        String expected="inappropriate";
        String actual=TextApi.checkComment("i will kill you");
        assertEquals(expected, actual);
    }

    @Test
    public void text_api_other_profanity () {
        // given -> a first user has already been created
        String expected="other_profanity";
        String actual=TextApi.checkComment("redneck");
        assertEquals(expected, actual);
    }

}