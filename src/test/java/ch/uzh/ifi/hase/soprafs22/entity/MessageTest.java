package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.MessageType;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
  private Message message;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // given
    message = new Message();
    message.setContent("testContent");
    message.setFrom("testFrom");
    message.setMessageType(MessageType.CommentText);
    message.setSessionStatus(SessionStatus.ONGOING);
  }

  @Test
  public void createChatUser_validInputs() {
    assertEquals(message.getContent(), "testContent");
    assertEquals(message.getFrom(), "testFrom");
    assertEquals(MessageType.CommentText, message.getMessageType());
    assertEquals(SessionStatus.ONGOING, message.getSessionStatus());
  }
}
