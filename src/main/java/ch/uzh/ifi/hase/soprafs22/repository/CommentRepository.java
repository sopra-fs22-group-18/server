package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long CommentId);
}