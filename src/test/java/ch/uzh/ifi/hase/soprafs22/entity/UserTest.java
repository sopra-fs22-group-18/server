package ch.uzh.ifi.hase.soprafs22.entity;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
  private User user;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    user = new User();

    user.setAvatarUrl("testUrl");
    user.setBio("testBio");
    user.setName("testName");
    user.setParticipatedSessions(1);
    user.setPassword("testPassword");
    user.setToken("testToken");
    user.setUserId(1L);
    user.setUserStatus(UserStatus.ONLINE);
    user.setUserType(UserType.STANDARD);
    user.setUsername("testUsername");
    user.setWonSessions(1);
  }

  @Test
  public void createUser_validInputs() {
    assertEquals(user.getAvatarUrl(), "testUrl");
    assertEquals(user.getBio(), "testBio");
    assertEquals(user.getName(), "testName");
    assertEquals(user.getParticipatedSessions(), 1);
    assertEquals(user.getPassword(), "testPassword");
    assertEquals(user.getToken(), "testToken");
    assertEquals(user.getUserId(), 1L);
    assertEquals(user.getUserStatus(), UserStatus.ONLINE);
    assertEquals(user.getUserType(), UserType.STANDARD);
    assertEquals(user.getUsername(), "testUsername");
    assertEquals(user.getWonSessions(), 1);
  }
}
