package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import org.junit.Before;
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
class SessionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    public void createUsers(){

    }

    @Test
    public void findByUsername_success(){
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

        entityManager.persist(session);
        entityManager.flush();

        //when
        Session found = sessionRepository.findBySessionId(session.getSessionId());

        //then
        assertNotNull(found.getSessionId());
        assertEquals(found.getSessionId(), session.getSessionId());
        assertEquals(found.getHost(), session.getHost());
        assertEquals(found.getIdentifier(), "hello");
        assertEquals(found.getTitle(), "test");
        assertEquals(found.getCreatedDate(), session.getCreatedDate());
        assertEquals(found.getImageUrl(), session.getImageUrl());
        assertEquals(found.getIsPrivate(), session.getIsPrivate());
        assertEquals(found.getMaxParticipants(), session.getMaxParticipants());
        assertEquals(found.getParticipants(), session.getParticipants());
        assertEquals(found.getWinner(), session.getWinner());
        assertEquals(found.getSessionStatus(), session.getSessionStatus());

    }

    @Test
    public void findByUsername_notSuccessfully_wrongUsername(){
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

        entityManager.persist(session);
        entityManager.flush();
        //when
        Session notFound =  sessionRepository.findBySessionId(session.getSessionId() + 1);
        //then
        assertNull(notFound);
    }

    @Test
    public void findBySessionStatusAndSessionId_success(){
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

        entityManager.persist(session);
        entityManager.flush();

        //when
        Session found = sessionRepository.findBySessionStatusAndIdentifier(session.getSessionStatus(), session.getIdentifier());

        //then
        assertNotNull(found);
        assertEquals(found.getSessionId(), session.getSessionId());
        assertEquals(found.getHost(), session.getHost());
        assertEquals(found.getIdentifier(), "hello");
        assertEquals(found.getTitle(), "test");
        assertEquals(found.getCreatedDate(), session.getCreatedDate());
        assertEquals(found.getImageUrl(), session.getImageUrl());
        assertEquals(found.getIsPrivate(), session.getIsPrivate());
        assertEquals(found.getMaxParticipants(), session.getMaxParticipants());
        assertEquals(found.getParticipants(), session.getParticipants());
        assertEquals(found.getWinner(), session.getWinner());
        assertEquals(found.getSessionStatus(), session.getSessionStatus());
    }

    @Test
    public void findBySessionStatusAndSessionId_notSuccessfully_wrongStatus(){
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

        entityManager.persist(session);
        entityManager.flush();

        //when
        Session found = sessionRepository.findBySessionStatusAndIdentifier(SessionStatus.ONGOING, session.getIdentifier());
        //then
        assertNull(found);
    }

    @Test
    public void findBySessionStatusAndSessionId_notSuccessfully_wrongSessionId(){
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

        entityManager.persist(session);
        entityManager.flush();

        //when
        Session found = sessionRepository.findBySessionStatusAndIdentifier(session.getSessionStatus(), "helo");

        //then
        assertNull(found);
    }

    @Test
    public void findBySessionStatusAndSessionId_notSuccessfully_bothWrong(){
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

        entityManager.persist(session);
        entityManager.flush();

        //when
        Session found = sessionRepository.findBySessionStatusAndIdentifier(SessionStatus.ONGOING, "helo");
        //then
        assertNull(found);
    }

    @Test
    public void findAllBySessionStatus_success() {
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

        entityManager.persist(session);
        entityManager.flush();

        List<Session> sessions = new ArrayList<Session>();
        sessions.add(session);

        //when
        List<Session> found = sessionRepository.findAllBySessionStatus(SessionStatus.CREATED);
        //then
        assertNotNull(found);
        assertEquals(sessions.get(0), found.get(0));
        assertEquals(sessions.get(0).getSessionStatus(), found.get(0).getSessionStatus());
        assertEquals(sessions.get(0).getSessionId(), found.get(0).getSessionId());
        assertEquals(sessions.get(0).getWinner(), found.get(0).getWinner());
        assertEquals(sessions.get(0).getParticipants(), found.get(0).getParticipants());
        assertEquals(found.get(0).getMaxParticipants(), sessions.get(0).getMaxParticipants());
        assertEquals(found.get(0).getIsPrivate(), false);
        assertEquals(found.get(0).getImageUrl(), sessions.get(0).getImageUrl());
        assertEquals(found.get(0).getCreatedDate(), sessions.get(0).getCreatedDate());
        assertEquals(found.get(0).getTitle(), "test");
        assertEquals(found.get(0).getIdentifier(), "hello");
        assertEquals(found.get(0).getHost(), host);
    }

    @Test
    public void findAllBySessionStatus_noSessions(){
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

        entityManager.persist(session);
        entityManager.flush();

        List<Session> emptyList = new ArrayList<Session>();

        //when
        List<Session> found = sessionRepository.findAllBySessionStatus(SessionStatus.ONGOING);
        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findAllBySessionStatusAndIsPrivate_success(){
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
        session.setIsPrivate(true);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persist(session);
        entityManager.flush();

        List<Session> sessions = new ArrayList<Session>();
        sessions.add(session);

        //when
        List<Session> found = sessionRepository.findAllBySessionStatusAndIsPrivate(session.getSessionStatus(), true);
        //then
        assertNotNull(found);
        assertEquals(sessions.get(0), found.get(0));
        assertEquals(sessions.get(0).getSessionStatus(), found.get(0).getSessionStatus());
        assertEquals(sessions.get(0).getSessionId(), found.get(0).getSessionId());
        assertEquals(sessions.get(0).getWinner(), found.get(0).getWinner());
        assertEquals(sessions.get(0).getParticipants(), found.get(0).getParticipants());
        assertEquals(found.get(0).getMaxParticipants(), sessions.get(0).getMaxParticipants());
        assertEquals(found.get(0).getIsPrivate(), true);
        assertEquals(found.get(0).getImageUrl(), sessions.get(0).getImageUrl());
        assertEquals(found.get(0).getCreatedDate(), sessions.get(0).getCreatedDate());
        assertEquals(found.get(0).getTitle(), "test");
        assertEquals(found.get(0).getIdentifier(), "hello");
        assertEquals(found.get(0).getHost(), host);
    }

    @Test
    public void findAllBySessionStatusAndIsPrivate_notSuccessfully_wrongPrivate(){
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
        session.setIsPrivate(true);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persist(session);
        entityManager.flush();

        List<Session> emptyList = new ArrayList<Session>();

        //when
        List<Session> found = sessionRepository.findAllBySessionStatusAndIsPrivate(session.getSessionStatus(), false);
        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findAllBySessionStatusAndIsPrivate_notSuccessfully_wrongSessionStatus(){
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
        session.setIsPrivate(true);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persist(session);
        entityManager.flush();

        List<Session> emptyList = new ArrayList<Session>();

        //when
        List<Session> found = sessionRepository.findAllBySessionStatusAndIsPrivate(SessionStatus.FINISHED, true);

        //then
        assertEquals(found, emptyList);
    }

    @Test
    public void findAllBySessionStatusAndIsPrivate_notSuccessfully_bothWrong(){
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
        session.setIsPrivate(true);
        session.setParticipants(participant);
        session.setTitle("test");

        entityManager.persist(session);
        entityManager.flush();

        List<Session> emptyList = new ArrayList<Session>();

        //when
        List<Session> found = sessionRepository.findAllBySessionStatusAndIsPrivate(SessionStatus.ONGOING, false);
        //then
        assertEquals(found, emptyList);
    }
}