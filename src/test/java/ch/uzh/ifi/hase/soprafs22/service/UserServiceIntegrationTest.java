package ch.uzh.ifi.hase.soprafs22.service;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
/*
/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

  @Qualifier("userRepository")
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  /* Field 'id' doesn't have a default value TODO: fix the createUser_validInputs_success() test!
  @Test
  public void createUser_validInputs_success() {
    // given
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setPassword("testPassword");
    testUser.setUsername("testUsername");

    // when
    User createdUser = userService.createUser(testUser);

    // then
<<<<<<< HEAD
    //assertEquals(testUser.getUserId(), createdUser.getUserId());
=======
    assertEquals(testUser.getUserId(), createdUser.getUserId());
>>>>>>> origin/master
    assertEquals(testUser.getPassword(), createdUser.getPassword());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    //assertEquals(true, createdUser.getLogged_in());
  }
  */
  /* Field 'id' doesn't have a default value TODO: fix the createUser_duplicateUsername_throwsException() test!
  @Test
  public void createUser_duplicateUsername_throwsException() {
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setPassword("123");
    testUser.setUsername("maxi");
    //User createdUser = userService.createUser(testUser);

    // attempt to create second user with same username
    User testUser2 = new User();
    userService.createUser(testUser);
    // change the password but forget about the username
    testUser2.setPassword("124");
    testUser2.setUsername("maxi");

    // check that an error is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
  }

   */
   /* Caused by: java.sql.SQLException:
      Access denied for user 'sql11482979'@'77-56-55-126.dclient.hispeed.ch' (using password: YES)
      TODO: Solve the problem in SQL databese and adjust the tests!

    @Test
    public void put_fails_id_not_existing() {

        User testUser = new User();
        testUser.setUsername("marko");
        testUser.setPassword("123");
        testUser.setUserId(1L);
        //testUser.setBirthday(null);
        //User createdUser = userService.createUser(testUser);

        // attempt to create second user with same username
        User testUser2 = new User();
        testUser2.setUserId(1L);
        testUser2.setUsername("marko");
        //testUser2.setBirthday(null);
        try{userService.updateUser(testUser2);System.out.print("Test fehlgeschlagen");}
        catch(Exception e){}
    }

    @Test
    public void username_already_exists() {

        User testUser = new User();
        testUser.setUsername("marko");
        testUser.setPassword("123");
        testUser.setUserId(1L);
        //User createdUser = userService.createUser(testUser);

        // attempt to create second user with same username
        User testUser2 = new User();
        testUser2.setUserId(2L);
        testUser2.setUsername("marko");
        //testUser2.setBirthday(null);
        try{userService.createUser(testUser2);System.out.print("Test fehlgeschlagen");}
        catch(Exception e){}
    }
    @Test
    public void userid_not_existing() {

        User testUser = new User();
        testUser.setUsername("marko");
        testUser.setPassword("123");
        testUser.setUserId(1L);
        //User createdUser = userService.createUser(testUser);

        // attempt to create second user with same username

        try{userService.getUser(2L); System.out.print("Test fehlgeschlagen");}
        catch(Exception e){
        }

    }

    */
}
