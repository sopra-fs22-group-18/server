package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private User host = new User();
    private User player = new User();
    private Session session = new Session();
    private Comment comment = new Comment();
    private List<Comment> comments = new ArrayList<>();
    private List<Comment> emptyList = new ArrayList<Comment>();
    private Set<User> participant = new HashSet<>();

    @BeforeEach
    private void given(){
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        participant = new HashSet<>();
        participant.add(player);

        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

        comments.add(comment);
    }

    @Test
    public void findAll_success(){
        //when
        List<Comment> found = commentRepository.findAll();

        //then
        assertEquals(found, comments);
        assertEquals(found.get(0), comments.get(0));
        assertEquals(found.get(0).getCommentId(), comments.get(0).getCommentId());
        assertEquals(found.get(0).getSession(), session);
        assertEquals(found.get(0).getCommentText(), "Test comment");
        assertEquals(found.get(0).getUser(), player);
        assertEquals(found.get(0).getCreatedDate(), comment.getCreatedDate());
    }

    @Test
    public void findBySession_success(){
        //when
        List<Comment> found = commentRepository.findBySession(session);

        //then
        assertEquals(found, comments);
        assertEquals(found.get(0), comments.get(0));
        assertEquals(found.get(0).getCommentId(), comments.get(0).getCommentId());
        assertEquals(found.get(0).getSession(), session);
        assertEquals(found.get(0).getCommentText(), "Test comment");
        assertEquals(found.get(0).getUser(), player);
        assertEquals(found.get(0).getCreatedDate(), comment.getCreatedDate());
    }

    @Test
    public void findBySession_notSuccessfully_wrongSession(){
        //given
        Session session1 = new Session();
        session1.setHost(host);
        session1.setSessionStatus(SessionStatus.CREATED);
        session1.setIdentifier("bye");
        session1.setIsPrivate(true);
        session1.setParticipants(participant);
        session1.setTitle("testtest");

        entityManager.persistAndFlush(session1);

        //when
        List<Comment> found = commentRepository.findBySession(session1);

        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findByCommentId_success(){
        //when
        Comment found = commentRepository.findByCommentId(comment.getCommentId());

        //then
        assertEquals(found, comment);
        assertEquals(found.getCommentId(), comment.getCommentId());
        assertEquals(found.getSession(), session);
        assertEquals(found.getCommentText(), "Test comment");
        assertEquals(found.getUser(), player);
        assertEquals(found.getCreatedDate(), comment.getCreatedDate());
    }

    @Test
    public void findByCommentId_notSuccessfully_wrongCommentId(){
        //when
        Comment found = commentRepository.findByCommentId(comment.getCommentId() + 1);

        //then
        assertNull(found);
    }
}