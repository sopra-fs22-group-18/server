package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.SessionRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Session Service
 * This class is the "worker" and responsible for all functionality related to
 * the session
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */

@Service
@Transactional
public class SessionService {

  private final Logger log = LoggerFactory.getLogger(SessionService.class);

  private final SessionRepository sessionRepository;
  private final UserRepository userRepository;

  @Autowired
  public SessionService(@Qualifier("sessionRepository") SessionRepository sessionRepository, @Qualifier("userRepository") UserRepository userRepository) {
    this.sessionRepository = sessionRepository;
    this.userRepository = userRepository;
  }

  public List<Session> getActiveSessions() {
      List<Session> activeSessions = this.sessionRepository.findAll();
      return activeSessions;
  }


  public Session createSession(Session newSession) {
    // update Session status
    newSession.setSessionStatus(SessionStatus.CREATED);
    newSession.setCreatedDate(new Date());

    // find host
    String baseErrorMessage = "Host with id %x was not found";
    Long hostId = newSession.getHost().getUserId();

    User host = userRepository.findById(hostId).orElseThrow(() ->
      new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,hostId))
      );

    // set host to user
    newSession.setHost(host);

    // save to repo and flush
    newSession = sessionRepository.save(newSession);
    sessionRepository.flush();

    log.debug("Created Information for Session: {}", newSession);
    return newSession;
  }

    public Session getSession(Long sessionId) {
      return this.sessionRepository.findBySessionId(sessionId);
    }

    public Session nextInQueue(Long userId) {
      List<Session> openSessions = this.sessionRepository.findAllBySessionStatus(SessionStatus.CREATED);
      Session nextSession = openSessions.isEmpty() ? null : openSessions.get(0);

      Optional<User> optionalUserFound = this.userRepository.findById(userId);
      String baseErrorMessage = "User with id %x was not found";
      User participant = optionalUserFound.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,userId))
        );

      nextSession.addParticipant(participant);

      sessionRepository.save(nextSession);
      sessionRepository.flush();

      return nextSession;
    }
}
