package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ChatUserTest {
  private ChatUser testChatUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testChatUser = new ChatUser();
    testChatUser.setName("testName");
    testChatUser.setSessionId((long) 1);
  }

  @Test
  public void createChatUser_validInputs() {
    assertEquals(testChatUser.getName(), "testName");
    assertEquals(testChatUser.getSessionId(), (long) 1);
  }
}
