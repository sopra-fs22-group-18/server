package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
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

import java.util.List;
import java.util.UUID;

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

  @Autowired
  public SessionService(@Qualifier("sessionRepository") SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public List<Session> getActiveSessions() {
      return this.sessionRepository.findByStatus(SessionStatus.CREATED);
  }


  public Session createSession(Session newSession) {
    newSession = sessionRepository.save(newSession);
    newSession.setStatus(SessionStatus.CREATED);
    sessionRepository.flush();

    log.debug("Created Information for Session: {}", newSession);
    return newSession;
  }


}
