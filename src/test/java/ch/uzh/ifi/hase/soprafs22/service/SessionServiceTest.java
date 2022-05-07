package ch.uzh.ifi.hase.soprafs22.service;

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

    Mockito.when(sessionRepository.save(Mockito.any())).thenReturn(testSession);

  }

  @Test
  public void createSession_validInputs_success() {

    Session createdSession = sessionService.createSession(testSession);

    // then
    Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any());

    assertEquals(testSession.getTitle(), createdSession.getTitle());
    assertEquals(testSession.getHost(), createdSession.getHost());

  }


}
