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

    @Test
    public void findAll_success(){
        //given
        User host = new User();
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        User player = new User();
        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        Set<User> participant = new HashSet<>();
        participant.add(player);

        Session session = new Session();
        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        Comment comment = new Comment();
        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);

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
    public void findAll_noComments(){
        List<Comment> emptyList = new ArrayList<Comment>();

        //when
        List<Comment> found = commentRepository.findAll();

        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findBySession_success(){
        //given
        User host = new User();
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        User player = new User();
        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        Set<User> participant = new HashSet<>();
        participant.add(player);

        Session session = new Session();
        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        Comment comment = new Comment();
        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

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
        User host = new User();
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        User player = new User();
        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        Set<User> participant = new HashSet<>();
        participant.add(player);

        Session session = new Session();
        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        Comment comment = new Comment();
        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

        Session session1 = new Session();
        session1.setHost(host);
        session1.setSessionStatus(SessionStatus.CREATED);
        session1.setIdentifier("bye");
        session1.setIsPrivate(true);
        session1.setParticipants(participant);
        session1.setTitle("testtest");

        entityManager.persistAndFlush(session1);

        List<Comment> emptyList = new ArrayList<Comment>();

        //when
        List<Comment> found = commentRepository.findBySession(session1);

        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findByCommentId_success(){
        //given
        User host = new User();
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        User player = new User();
        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        Set<User> participant = new HashSet<>();
        participant.add(player);

        Session session = new Session();
        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        Comment comment = new Comment();
        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

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
        //given
        User host = new User();
        host.setUsername("Host");
        host.setPassword("Password");
        host.setUserStatus(UserStatus.ONLINE);
        host.setToken("1");

        entityManager.persistAndFlush(host);

        User player = new User();
        player.setUsername("Player");
        player.setPassword("Password");
        player.setUserStatus(UserStatus.ONLINE);
        player.setToken("2");

        entityManager.persistAndFlush(player);

        Set<User> participant = new HashSet<>();
        participant.add(player);

        Session session = new Session();
        session.setHost(host);
        session.setSessionStatus(SessionStatus.CREATED);
        session.setIdentifier("hello");
        session.setIsPrivate(false);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persistAndFlush(session);

        Comment comment = new Comment();
        comment.setUser(player);
        comment.setSession(session);
        comment.setCommentText("Test comment");

        entityManager.persistAndFlush(comment);

        //when
        Comment found = commentRepository.findByCommentId(comment.getCommentId() + 1);

        //then
        assertNull(found);
    }
}