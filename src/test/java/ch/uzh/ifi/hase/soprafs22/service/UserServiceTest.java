package ch.uzh.ifi.hase.soprafs22.service;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  private User testUser2;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setPassword("testPassword");
    testUser.setUsername("testUsername");

    testUser2 = new User();
    testUser2.setPassword("testPassword2");
    testUser2.setUsername("testUsername2");


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
  public void returnAllUsersTest() {
    List<User> userList = new ArrayList<>();
    userList.add(testUser);
    userList.add(testUser2);

    Mockito.when(userRepository.findAll()).thenReturn(userList);

    List<User> returnedList = userService.getUsers();
    assertEquals(userList.size(), returnedList.size());
  }

  @Test
  public void checkingUserSuccess() {
    Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);

    User returnedUser = userService.checkingUser(testUser);

    assertEquals(testUser, returnedUser);
  }

  @Test
  public void checkingUserFailureNull() {
    Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);

    assertThrows(ResponseStatusException.class, () -> userService.checkingUser(testUser));
  }

  @Test
  public void checkingUserFailureWrongPassword() {
    Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(testUser);

    assertThrows(ResponseStatusException.class, () -> userService.checkingUser(testUser2));
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

  @Test
  public void checkAuthorizationFailure() {
    UserPutDTO testDTOUser = new UserPutDTO();
    testDTOUser.setUserId(1L);

    assertThrows(ResponseStatusException.class, () -> userService.checkAuthorization(testDTOUser, 2L));
  }
  
  @Test
  public void updateUserEverythingSuccess() {
    Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testUser));

    User updatedUser = new User();

    updatedUser.setAvatarUrl("testUpUrl");
    updatedUser.setUsername("testUpUsername");
    updatedUser.setPassword("testUpPassword");
    updatedUser.setBio("testUpBio");
    updatedUser.setName("testUpName");
    updatedUser.setUserId(1L);

    User updatedTestUser = userService.updateUser(updatedUser);
    assertEquals(updatedUser.getAvatarUrl(), updatedTestUser.getAvatarUrl());
    assertEquals(updatedUser.getUsername(), updatedTestUser.getUsername());
    assertEquals(updatedUser.getPassword(), updatedTestUser.getPassword());
    assertEquals(updatedUser.getBio(), updatedTestUser.getBio());
    assertEquals(updatedUser.getName(), updatedTestUser.getName());
  } 

  @Test
  public void updateUserEverythingFailureBlank() {
    Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testUser));

    User updatedUser = new User();

    updatedUser.setAvatarUrl("");
    updatedUser.setUsername("");
    updatedUser.setPassword("");
    updatedUser.setBio("");
    updatedUser.setName("");
    updatedUser.setUserId(1L);

    User updatedTestUser = userService.updateUser(updatedUser);
    assertEquals(updatedUser.getAvatarUrl(), updatedTestUser.getAvatarUrl());
    assertEquals(updatedUser.getUsername(), updatedTestUser.getUsername());
    assertEquals(updatedUser.getPassword(), updatedTestUser.getPassword());
    assertEquals(updatedUser.getBio(), updatedTestUser.getBio());
    assertEquals(updatedUser.getName(), updatedTestUser.getName());
  } 

  @Test
  public void updateUserNotFound() {
    Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    User updatedUser = new User();

    assertThrows(ResponseStatusException.class, () -> userService.updateUser(updatedUser));
  } 

}
