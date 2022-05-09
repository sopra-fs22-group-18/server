package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionRepository")
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findBySessionId(Long sessionId);

    Session findBySessionStatusAndIdentifier(SessionStatus sessionStatus, String identifier);

    List<Session> findAllBySessionStatus(SessionStatus sessionStatus);

    List<Session> findAllBySessionStatusAndIsPrivate(SessionStatus sessionStatus, boolean isPrivate);

}