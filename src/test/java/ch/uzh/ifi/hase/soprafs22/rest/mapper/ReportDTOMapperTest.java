package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.User;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;

import ch.uzh.ifi.hase.soprafs22.rest.dto.ReportGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ReportPostDTO;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;


import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentPostDTO;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation
 * works.
 */
public class ReportDTOMapperTest {
  @Test

  public void testCreateReport_fromReportPostDTO_toReport_success() {
    // create UserPostDTO
    UserPostDTO userPostDTO = new UserPostDTO();
    userPostDTO.setUsername("username");
    userPostDTO.setPassword("password");
    User user = UserDTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);



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

    CommentPostDTO commentPostDTO = new CommentPostDTO();
    commentPostDTO.setUser(user);
    commentPostDTO.setSession(session);
    commentPostDTO.setCommentText("testcommenttext");
    final Date datena = new Date();
    commentPostDTO.setCreatedDate(datena);
    Comment comment = CommentDTOMapper.INSTANCE.convertCommentPostDTOtoEntity(commentPostDTO);
    



    ReportPostDTO reportPostDTO = new ReportPostDTO();
    reportPostDTO.setComment(comment);
    reportPostDTO.setSession(session);
    reportPostDTO.setUser(user);
    reportPostDTO.setDescription("F*** you");
    reportPostDTO.setReason(ReportReason.SEXUAL_HARASSMENT);
    reportPostDTO.setCreatedDate(datena);
    Report report = ReportDTOMapper.INSTANCE.convertReportPostDTOtoEntity(reportPostDTO);


    // check content
    assertEquals(reportPostDTO.getComment(), report.getComment());
    assertEquals(reportPostDTO.getSession(), report.getSession());
    assertEquals(reportPostDTO.getUser(), report.getUser());
    assertEquals(reportPostDTO.getDescription(), report.getDescription());  
    assertEquals(reportPostDTO.getReason(), report.getReason());
    assertEquals(reportPostDTO.getCreatedDate(), report.getCreatedDate());

  }

  @Test
  public void testGetReport_fromReport_toReportGetDTO_success() {
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
    


    Report report=new Report();
    report.setComment(comment);
    report.setSession(session);
    report.setUser(user);
    report.setDescription(("testdescription"));
    report.setReason(ReportReason.SEXUAL_HARASSMENT);
    report.setCreatedDate((datena));

    // MAP -> Create ReportGetDTO
    ReportGetDTO reportGetDTO = ReportDTOMapper.INSTANCE.convertEntityToReportGetDTO(report);

    // check content
    assertEquals(report.getComment(), reportGetDTO.getComment());
    assertEquals(report.getSession(), reportGetDTO.getSession());
    assertEquals(report.getUser(), reportGetDTO.getUser());
    assertEquals(report.getDescription(), reportGetDTO.getDescription());
    assertEquals(report.getReason(), reportGetDTO.getReason());
    assertEquals(report.getCreatedDate(), reportGetDTO.getCreatedDate());
  }
}
