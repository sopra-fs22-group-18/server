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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
      List<Session> activeSessions = this.sessionRepository.findAllBySessionStatus(SessionStatus.CREATED);
      return activeSessions;
  }


  public List<Session> getAllSessions() {
    List<Session> activeSessions = this.sessionRepository.findAllBySessionStatus(SessionStatus.CREATED);
    List<Session> finished = this.sessionRepository.findAllBySessionStatus(SessionStatus.FINISHED);
    List<Session> ongoing = this.sessionRepository.findAllBySessionStatus(SessionStatus.ONGOING);
    List<Session> newList = Stream.concat(activeSessions.stream(), finished.stream()).collect(Collectors.toList());
    List<Session> newList2 = Stream.concat(newList.stream(), ongoing.stream()).collect(Collectors.toList());
    return newList2;
}


  public Session createSession(Session newSession) {
    // update Session status
    newSession.setSessionStatus(SessionStatus.CREATED);
    newSession.setCreatedDate(new Date());


    // create identifier
    newSession.setIdentifier(createRandomNumbeString());

    // find host
    String baseErrorMessage = "Host with id %x was not found";
    Long hostId = newSession.getHost().getUserId();


    User host = userRepository.findByUserId(hostId);
    if(host == null) {
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,hostId));
    }


    // set host to user
    newSession.setHost(host);
    newSession.setCreatedDate(new Date());

    // save to repo and flush
    newSession = sessionRepository.save(newSession);
    sessionRepository.flush();

    log.debug("Created Information for Session: {}", newSession);
    return newSession;
  }

    public Session getSession(Long sessionId) {
      return this.sessionRepository.findBySessionId(sessionId);
    }

    public Session joinSessionByIdentifier(Long userId, String identifier) {
      Session openSession = this.sessionRepository.findBySessionStatusAndIdentifier(SessionStatus.CREATED, identifier);

      if (openSession == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found");
      }
      User participant = this.userRepository.findByUserId(userId);
      String baseErrorMessage = "User with id %x was not found";
      if (participant == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,userId));
      }
      
      openSession.addParticipant(participant);

      sessionRepository.save(openSession);
      sessionRepository.flush();

      return openSession;
    }

    public Session joinSessionByQueue(Long userId) {
      List<Session> openSessions = this.sessionRepository.findAllBySessionStatusAndIsPrivate(SessionStatus.CREATED, false);
      Session nextSession = openSessions.isEmpty() ? null : openSessions.get(0);

      User participant = this.userRepository.findByUserId(userId);
      String baseErrorMessage = "User with id %x was not found";
      if (participant == null) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,userId));
      }

      nextSession.addParticipant(participant);

      sessionRepository.save(nextSession);
      sessionRepository.flush();

      return nextSession;
    }

    public Session removeParticipant(Long sessionId, Long userId) {

      Session currentSession = this.sessionRepository.findBySessionId(sessionId);
      User participant = this.userRepository.findByUserId(userId);
      String baseErrorMessage = "User with id %x was not found";
      if (participant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage,userId));
      }
      currentSession.removeParticipant(participant);

      sessionRepository.save(currentSession);
      sessionRepository.flush();

      return currentSession;
    }

    private String createRandomNumbeString() {
      Random random = new Random();
      int number = random.nextInt(999999);

      // to be super safe one could save the number and compare against a runtime array of previously rolled numbers

      return String.format("%06d", number);
    }
}
