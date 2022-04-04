package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UserDTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class UserDTOMapperTest {
  @Test
  public void testCreateUser_fromUserPostDTO_toUser_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setName("name");
    userPostDTO.setUsername("username");

    // MAP -> Create user
    User user = UserDTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getName(), user.getName());
    assertEquals(userPostDTO.getUsername(), user.getUsername());
  }

  @Test
  public void testGetUser_fromUser_toUserGetDTO_success() {
    // create User
    User user = new User();
    user.setName("Firstname Lastname");
    user.setUsername("firstname@lastname");
    user.setStatus(UserStatus.OFFLINE);
    user.setToken("1");

    // MAP -> Create UserGetDTO
    UserGetDTO userGetDTO = UserDTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

    // check content
    assertEquals(user.getUserId(), userGetDTO.getUserId());
    assertEquals(user.getName(), userGetDTO.getName());
    assertEquals(user.getUsername(), userGetDTO.getUsername());
    assertEquals(user.getStatus(), userGetDTO.getStatus());
  }
}