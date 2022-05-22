package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.SessionRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SessionServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private SessionRepository sessionRepository;

  @InjectMocks
  private SessionService sessionService;

  private User host;
  private User participant;
  private Session testSession;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    // given
    host = new User();
    host.setPassword("testPassword");
    host.setUsername("host");
    host.setUserId(1L);

    participant = new User();
    participant.setPassword("testPassword2");
    participant.setUsername("participant");
    participant.setUserId(2L);

    // when the users are being looked for, return the dummy users.
    Mockito.when(userRepository.findByUserId(1L)).thenReturn(host);
    Mockito.when(userRepository.findByUserId(2L)).thenReturn(participant);

    testSession = new Session();
    testSession.setTitle("testSession");
    testSession.setHost(host);
    testSession.setMaxParticipants(3);
    testSession.setSessionStatus(SessionStatus.CREATED);
    testSession.setSessionId(3L);

    Mockito.when(sessionRepository.save(Mockito.any())).thenReturn(testSession);
    Mockito.when(sessionRepository.findBySessionId(3L)).thenReturn(testSession);

  }

  @Test
  public void createSession_validInputs_success() {

    Session createdSession = sessionService.createSession(testSession);

    // then
    Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testSession.getTitle(), createdSession.getTitle());
    assertEquals(testSession.getHost(), createdSession.getHost());

  }

  @Test
  public void createSession_invalid_userId() {
      host.setUserId(5L);

      Mockito.when(userRepository.findByUserId(5L)).thenReturn(null);

      Throwable thrown = assertThrows(ResponseStatusException.class, () -> sessionService.createSession(testSession));

      assertEquals("404 NOT_FOUND \"Host with id 5 was not found\"", thrown.getMessage());

    }

  @Test
  public void joinSessionByQueue_validInputs() {

      // make sure the sessionRepository returns the testSession
     List<Session> testOpenSessions = new ArrayList<Session>();
     testOpenSessions.add(testSession);
     Mockito.when(sessionRepository.findAllBySessionStatusAndIsPrivate(SessionStatus.CREATED, false)).thenReturn(testOpenSessions);

     Session joinedSession = sessionService.joinSessionByQueue(participant.getUserId());
     testSession.addParticipant(participant);

     assertEquals(testSession.getSessionId(), joinedSession.getSessionId());
     assertEquals(testSession.getParticipants(), joinedSession.getParticipants());

    }

  @Test
  public void joinSessionByIdentifier_validInputs() {
    String testIdentifier = "123123";
     Mockito.when(sessionRepository.findBySessionStatusAndIdentifier(SessionStatus.CREATED, testIdentifier)).thenReturn(testSession);

     Session joinedSession = sessionService.joinSessionByIdentifier(participant.getUserId(), testIdentifier);
     testSession.addParticipant(participant);

     assertEquals(testSession.getSessionId(), joinedSession.getSessionId());
     assertEquals(testSession.getParticipants(), joinedSession.getParticipants());

    }
    
  @Test
  public void joinSession_invalid_userId() {

      //change participant so it won't be found
      participant.setUserId(5L);
      Mockito.when(userRepository.findByUserId(5L)).thenReturn(null);

      // make sure the sessionRepository returns the testSession
      List<Session> testOpenSessions = new ArrayList<Session>();
      testOpenSessions.add(testSession);
      Mockito.when(sessionRepository.findAllBySessionStatus(SessionStatus.CREATED)).thenReturn(testOpenSessions);

      Throwable thrown = assertThrows(ResponseStatusException.class, () -> sessionService.joinSessionByQueue(participant.getUserId()));

      assertEquals("404 NOT_FOUND \"User with id 5 was not found\"", thrown.getMessage());
  }

  @Test
  public void removeParticipant_validInputs() {

      testSession.addParticipant(participant);

      Session removedSession = sessionService.removeParticipant(testSession.getSessionId(), participant.getUserId());

      testSession.removeParticipant(participant);

      assertEquals(testSession.getSessionId(), removedSession.getSessionId());
      assertEquals(testSession.getParticipants(), removedSession.getParticipants());
  }

  @Test
  public void removeParticipant_invalid_userId() {
      //change participant so it won't be found
      participant.setUserId(5L);
      Mockito.when(userRepository.findByUserId(5L)).thenReturn(null);

      Throwable thrown = assertThrows(ResponseStatusException.class, () -> sessionService.removeParticipant(testSession.getSessionId(), participant.getUserId()));
      assertEquals("404 NOT_FOUND \"User with id 5 was not found\"", thrown.getMessage());

  }

  @Test
  public void getActiveSessions_valid() {

      // make sure the sessionRepository returns the testSession
      List<Session> testOpenSessions = new ArrayList<Session>();
      testOpenSessions.add(testSession);
      Mockito.when(sessionRepository.findAllBySessionStatus(SessionStatus.CREATED)).thenReturn(testOpenSessions);

      List<Session> returnedSessions = sessionService.getActiveSessions();

      assertEquals(testOpenSessions, returnedSessions);
  }

  @Test
  public void getOneSession_valid() {

      Session returnedSession = sessionService.getSession(testSession.getSessionId());

      assertEquals(testSession.getSessionId(), returnedSession.getSessionId());
      assertEquals(testSession.getTitle(), returnedSession.getTitle());
  }

  @Test
  public void checkSessionStatus_Created() {
      SessionStatus returnedStatus = sessionService.checkSessionStatus(3L);
      assertEquals(SessionStatus.CREATED, returnedStatus);
  }
}
