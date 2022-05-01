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

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setPassword("testPassword");
    testUser.setUsername("testUsername");

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createUser_validInputs_success() {
    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    User createdUser = userService.createUser(testUser);

    // then
    Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testUser.getUserId(), createdUser.getUserId());
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.ONLINE, createdUser.getUserStatus());
  }

  @Test
  public void createUser_duplicateUsername_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    //Mockito.when(userRepository.findByPassword(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
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







  @Test
  public void createUser_duplicateInputs_throwsException() {
    // given -> a first user has already been created
    userService.createUser(testUser);

    // when -> setup additional mocks for UserRepository
    //Mockito.when(userRepository.findByPassword(Mockito.any())).thenReturn(testUser);
    Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);

    // then -> attempt to create second user with same user -> check that an error
    // is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser));
  }

}
