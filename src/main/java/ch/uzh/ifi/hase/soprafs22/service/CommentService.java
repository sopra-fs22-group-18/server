package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs22.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


/**
 * Comment Service
 * This class is the "worker" and responsible for all functionality related to
 * the session
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */

@Service
@Transactional
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public CommentService(@Qualifier("commentRepository") CommentRepository commentRepository,
                          @Qualifier("sessionRepository") SessionRepository sessionRepository) {

        this.commentRepository = commentRepository;
        this.sessionRepository = sessionRepository;
    }


    public List<Comment> getSessionComments(Long sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session != null) {
            List<Comment> sessionComments = this.commentRepository.findBySession(session);

            return sessionComments;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There exists no session under this sessionId!");
        }
    }


    public Comment createSessionComment(Comment newSessionComment) {

        // find session
        Long sessionId = newSessionComment.getSession().getSessionId();
        Session session = sessionRepository.findBySessionId(sessionId);

        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There exists no session under this sessionId!");
        } else {
            // set host to user
            newSessionComment.setCreatedDate(new java.util.Date());
            newSessionComment.setSession(session);

            // save to repo and flush
            newSessionComment = commentRepository.save(newSessionComment);
            sessionRepository.flush();

            log.debug("Created Information for SessionComment: {}", newSessionComment);
            return newSessionComment;
        }

    }

}