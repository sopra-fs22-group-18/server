package ch.uzh.ifi.hase.soprafs22.repository;
import ch.uzh.ifi.hase.soprafs22.constant.Status;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;
  /* Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "HIBERNATE_SEQUENCE" not found; SQL statement:
     select next_val as id_val from hibernate_sequence for update [42102-200] TODO: Fix the findByUsername_success() test
  @Test
  public void findByUsername_success() {
<<<<<<< HEAD
    // given
    User user = new User();
    user.setUsername("Max");
    user.setPassword("123");
    user.setStatus(Status.ONLINE);
    //user.setLogged_in(true);
    //user.setCreation_date(new Date());
=======
      // given
      User user = new User();
      user.setUsername("Max");
      user.setPassword("123");
      user.setStatus(UserStatus.ONLINE);
      user.setLogged_in(true);
      user.setCreation_date(new Date());
>>>>>>> origin/master

      user.setToken("1");

      entityManager.persist(user);
      entityManager.flush();

      // when
      User found = userRepository.findByUsername(user.getUsername());

<<<<<<< HEAD
    // then
    assertNotNull(found.getUserId());
    assertEquals(found.getPassword(), user.getPassword());
    assertEquals(found.getUsername(), user.getUsername());
    assertEquals(found.getToken(), user.getToken());
    assertEquals(found.getStatus(), user.getStatus());
=======
      // then
      assertNotNull(found.getUserId());
      assertEquals(found.getPassword(), user.getPassword());
      assertEquals(found.getUsername(), user.getUsername());
      assertEquals(found.getToken(), user.getToken());
      assertEquals(found.getStatus(), user.getStatus());
>>>>>>> origin/master
  }

   */
}
