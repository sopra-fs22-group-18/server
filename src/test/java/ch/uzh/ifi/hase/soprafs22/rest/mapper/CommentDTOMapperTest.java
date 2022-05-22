package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentPostDTO;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class CommentDTOMapperTest {
  @Test

  public void testCreateComment_fromCommentPostDTO_toComment_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");
    // MAP -> Create user
    User user = UserDTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);


    SessionPostDTO sessionPostDTO = new SessionPostDTO();
    sessionPostDTO.setHost(user);
    sessionPostDTO.setMaxParticipants(5);
    sessionPostDTO.setTitle("testtile");
    sessionPostDTO.setImageUrl("testurl");
    sessionPostDTO.setIsPrivate(true);
    Session session = SessionDTOMapper.INSTANCE.convertSessionPostDTOtoEntity(sessionPostDTO);

    CommentPostDTO commentPostDTO = new CommentPostDTO();
    commentPostDTO.setUser(user);
    commentPostDTO.setSession(session);
    commentPostDTO.setCommentText("testcommenttext");
    final Date datena = new Date();
    commentPostDTO.setCreatedDate(datena);
    Comment comment = CommentDTOMapper.INSTANCE.convertCommentPostDTOtoEntity(commentPostDTO);

    // check content
    assertEquals(commentPostDTO.getUser(), comment.getUser());
    assertEquals(commentPostDTO.getSession(), comment.getSession());
    assertEquals(commentPostDTO.getCommentText(), comment.getCommentText());
    assertEquals(commentPostDTO.getCreatedDate(), comment.getCreatedDate());
  }

  @Test
  public void testGetComment_fromComment_toCommentGetDTO_success() {
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


    Comment comment=new Comment();
    comment.setCommentId(1L);
    comment.setSession(session);
    comment.setUser(user);
    comment.setCommentText("test_comment_text");
    final Date datena = new Date();
    comment.setCreatedDate(datena);
    
    // MAP -> Create CommentGetDTO
    CommentGetDTO commentGetDTO = CommentDTOMapper.INSTANCE.convertEntityToCommentGetDTO(comment);

    // check content
    assertEquals(comment.getCommentId(), commentGetDTO.getCommentId());
    assertEquals(comment.getSession(), commentGetDTO.getSession());
    assertEquals(comment.getUser(), commentGetDTO.getUser());
    assertEquals(comment.getCommentText(), commentGetDTO.getCommentText());
    assertEquals(comment.getCreatedDate(), commentGetDTO.getCreatedDate());
  }
}
