package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class ChatUserTest {
  private ChatUser testChatUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testChatUser = new ChatUser();
    testChatUser.name = ("testName");
    testChatUser.sessionId = (long) 1; 
  }

  @Test
  public void createChatUser_validInputs() {
    // when -> any object is being save in the userRepository -> return the dummy

    assertEquals(testChatUser.name, "testName");
    assertEquals(testChatUser.sessionId, (long) 1);
  }
}
