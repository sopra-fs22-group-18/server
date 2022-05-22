package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.MessageType;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

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
    message.setMessagetype(MessageType.CommentText);
    message.setSessionStatus(SessionStatus.ONGOING);
  }

  @Test
  public void createChatUser_validInputs() {
    assertEquals(message.getContent(), "testContent");
    assertEquals(message.getFrom(), "testFrom");
    assertEquals(MessageType.CommentText, message.getMessagetype());
    assertEquals(SessionStatus.ONGOING, message.getSessionStatus());
  }
}
