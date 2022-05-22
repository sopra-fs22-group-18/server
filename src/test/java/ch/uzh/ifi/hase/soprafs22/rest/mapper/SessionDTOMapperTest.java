package ch.uzh.ifi.hase.soprafs22.rest.mapper;
import java.util.HashSet;
import java.util.Set;


import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class SessionDTOMapperTest {
  @Test

  public void testCreateSession_fromSessionPostDTO_toSession_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");
    // MAP -> Create user
    User host = UserDTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // check content
    assertEquals(userPostDTO.getUsername(), host.getUsername());
  
    SessionPostDTO sessionPostDTO = new SessionPostDTO();
    sessionPostDTO.setHost(host);
    sessionPostDTO.setMaxParticipants(5);
    sessionPostDTO.setTitle("testtile");
    sessionPostDTO.setImageUrl("testurl");
    sessionPostDTO.setIsPrivate(true);

    // MAP -> Create user
    Session session = SessionDTOMapper.INSTANCE.convertSessionPostDTOtoEntity(sessionPostDTO);

    // check content
    assertEquals(sessionPostDTO.getHost(), session.getHost());
    assertEquals(sessionPostDTO.getMaxParticipants(), session.getMaxParticipants());
    assertEquals(sessionPostDTO.getTitle(), session.getTitle());
    assertEquals(sessionPostDTO.getImageUrl(), session.getImageUrl());
    assertEquals(sessionPostDTO.getIsPrivate(), session.getIsPrivate());
  }
  
  




    

  @Test
  public void testGetSession_fromSession_toSessionGetDTO_success() {
    // create User
    User user = new User();
    user.setUsername("firstname@lastname");
    user.setPassword("password");
    user.setUserStatus(UserStatus.OFFLINE);

    Session session=new Session();
    session.setSessionId(1L);
    session.setHost(user);
    session.setTitle(("title1"));
    session.setMaxParticipants((5));
    Set<User> participants = new HashSet<>();
    session.setParticipants((participants));
    session.setImageUrl("testu3l");
    session.setIdentifier("testid");
    session.setIsPrivate(true);

    // MAP -> Create UserGetDTO
    SessionGetDTO sessionGetDTO = SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session);

    // check content
    assertEquals(session.getSessionId(), sessionGetDTO.getSessionId());
    assertEquals(session.getHost(), sessionGetDTO.getHost());
    assertEquals(session.getTitle(), sessionGetDTO.getTitle());
    assertEquals(session.getMaxParticipants(), sessionGetDTO.getMaxParticipants());
    assertEquals(session.getParticipants(), sessionGetDTO.getParticipants());
    assertEquals(session.getImageUrl(), sessionGetDTO.getImageUrl());
    assertEquals(session.getIdentifier(), sessionGetDTO.getIdentifier());
    assertEquals(session.getIsPrivate(), sessionGetDTO.getIsPrivate());

  }
}
