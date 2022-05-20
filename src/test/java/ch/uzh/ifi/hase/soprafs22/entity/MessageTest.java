package ch.uzh.ifi.hase.soprafs22.entity;

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
  }

  @Test
  public void createChatUser_validInputs() {
    assertEquals(message.getContent(), "testContent");
    assertEquals(message.getFrom(), "testFrom");
  }
}
