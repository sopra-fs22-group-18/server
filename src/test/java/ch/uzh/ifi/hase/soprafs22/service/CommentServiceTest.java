package ch.uzh.ifi.hase.soprafs22.service;
import ch.qos.logback.classic.Logger;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs22.repository.SessionRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class CommentServiceTest {

  @Mock
  private CommentRepository commentRepository;

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CommentService commentService;

  private User testUser;
  private Session testSession;
  private Comment testComment;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    testUser = new User();
    testUser.setPassword("testPassword");
    testUser.setUsername("testUsername");

    testSession = new Session();

    testComment = new Comment();
    testComment.setCommentText("testCommentText");
    testComment.setCreatedDate(new Date());
    testComment.setSession(testSession);
    testComment.setUser(testUser);

    // when -> any object is being save in the userRepository -> return the dummy
    // testUser
    Mockito.when(commentRepository.save(Mockito.any())).thenReturn(testComment);
    Mockito.when(sessionRepository.save(Mockito.any())).thenReturn(testSession);
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
  }

  @Test
  public void createCommentFromSessionTest() {
    Mockito.when(sessionRepository.findBySessionId(Mockito.anyLong())).thenReturn(testSession);
    Mockito.when(userRepository.findByUserId(Mockito.anyLong())).thenReturn(testUser);
    
    Comment returnedComment = commentService.createCommentFromSession("testCommentText", 1L, 1L);

    assertEquals(testComment.getCommentText(), returnedComment.getCommentText());
    assertEquals(testComment.getSession(), returnedComment.getSession());
    assertEquals(testComment.getUser(), returnedComment.getUser());
  }
}

