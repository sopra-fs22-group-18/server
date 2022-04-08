package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySession(Session session);
}