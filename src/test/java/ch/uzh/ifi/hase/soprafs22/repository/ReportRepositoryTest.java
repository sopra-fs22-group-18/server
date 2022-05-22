package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
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
class ReportRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReportRepository reportRepository;

    private User host = new User();
    private User player = new User();
    private Session session = new Session();
    private Comment comment = new Comment();
    private Report report = new Report();
    private List<Comment> comments = new ArrayList<>();
    private List<Comment> emptyList = new ArrayList<Comment>();
    private Set<User> participant = new HashSet<>();
    private List<Report> reportList = new ArrayList<>();

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

        report = new Report();
        report.setDescription("racist comment");
        report.setReason(ReportReason.RACISM);
        report.setUser(player);
        report.setSession(session);

        entityManager.persistAndFlush(report);

        reportList.add(report);
    }

    @Test
    public void findByReportId_success(){
        //when
        Report found = reportRepository.findByReportId(report.getReportId());

        //then
        assertEquals(found.getReportId(), report.getReportId());
        assertEquals(found.getCreatedDate(), report.getCreatedDate());
        assertEquals(found.getDescription(), "racist comment");
        assertEquals(found.getReason(), ReportReason.RACISM);
        assertEquals(found.getSession(), session);
        assertEquals(found.getUser(), player);
    }

    @Test
    public void findByReportId_notSuccessfully_wrongId(){
        //when
        Report found = reportRepository.findByReportId(report.getReportId()+1);

        //then
        assertNotEquals(found, report);
        assertNull(found);
    }

    @Test
    public void findBySession_oneReport_success(){
        //when
        List<Report> found = reportRepository.findBySession(session);

        //then
        assertEquals(found, reportList);
        assertEquals(found.get(0).getUser(), player);
        assertEquals(found.get(0).getSession(), session);
        assertEquals(found.get(0).getReportId(), report.getReportId());
        assertEquals(found.get(0).getReason(), ReportReason.RACISM);
        assertEquals(found.get(0).getDescription(), "racist comment");
        assertEquals(found.get(0).getCreatedDate(), report.getCreatedDate());
    }

    @Test
    public void findBySession_twoReports_success(){
        //additional given
        Comment comment1 = new Comment();
        comment1.setUser(player);
        comment1.setSession(session);
        comment1.setCommentText("Test comment");

        entityManager.persistAndFlush(comment1);

        Report report1 = new Report();
        report1.setDescription("she threaded me");
        report1.setReason(ReportReason.THREAT);
        report1.setUser(player);
        report1.setSession(session);

        entityManager.persistAndFlush(report1);

        reportList.add(report1);

        //when
        List<Report> found = reportRepository.findBySession(session);

        //then
        assertEquals(found, reportList);
        assertEquals(found.get(0).getUser(), player);
        assertEquals(found.get(0).getSession(), session);
        assertEquals(found.get(0).getReportId(), report.getReportId());
        assertEquals(found.get(0).getReason(), ReportReason.RACISM);
        assertEquals(found.get(0).getDescription(), "racist comment");
        assertEquals(found.get(0).getCreatedDate(), report.getCreatedDate());
        assertEquals(found.get(1).getUser(), player);
        assertEquals(found.get(1).getSession(), session);
        assertEquals(found.get(1).getReportId(), report1.getReportId());
        assertEquals(found.get(1).getReason(), ReportReason.THREAT);
        assertEquals(found.get(1).getDescription(), "she threaded me");
        assertEquals(found.get(1).getCreatedDate(), report1.getCreatedDate());
    }

    @Test
    public void findBySession_notSuccessfully_wrongSession(){
        //additional given
        Session session1 = new Session();
        session1.setHost(host);
        session1.setSessionStatus(SessionStatus.ONGOING);
        session1.setIdentifier("Bye");
        session1.setIsPrivate(true);
        session1.setParticipants(participant);
        session1.setTitle("title");

        entityManager.persistAndFlush(session1);


        //when
        List<Report> found = reportRepository.findBySession(session1);

        //then
        assertEquals(found, emptyList);
    }
}