package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ReportRepository;
import ch.uzh.ifi.hase.soprafs22.repository.SessionRepository;
import ch.uzh.ifi.hase.soprafs22.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private User host;
    private User participant;
    private Session testSession;
    private Comment testComment;
    private Report testReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        host = new User();
        host.setPassword("testPassword");
        host.setUsername("host");
        host.setUserId(1L);


        participant = new User();
        participant.setPassword("testPassword2");
        participant.setUsername("participant");
        participant.setUserId(2L);



        // when the users are being looked for, return the dummy users.

        Mockito.when(userRepository.findByUserId(1L)).thenReturn(host);
        Mockito.when(userRepository.findByUserId(2L)).thenReturn(participant);


        testSession = new Session();
        testSession.setTitle("testSession");
        testSession.setHost(host);
        testSession.setMaxParticipants(3);
        testSession.setSessionStatus(SessionStatus.CREATED);
        testSession.setSessionId(3L);

        Mockito.when(sessionRepository.save(Mockito.any())).thenReturn(testSession);
        Mockito.when(sessionRepository.findBySessionId(3L)).thenReturn(testSession);

        testComment = new Comment();
        testComment.setCommentId(6L);
        testComment.setSession(testSession);
        testComment.setUser(participant);
        testComment.setCommentText("This is a test comment");

        Mockito.when(commentRepository.save(Mockito.any())).thenReturn(testComment);
        Mockito.when(commentRepository.findByCommentId(6L)).thenReturn(testComment);

        testReport = new Report();
        testReport.setReportId(7L);
        testReport.setSession(testSession);
        testReport.setUser(participant);
        testReport.setReason(ReportReason.THREAT);
        testReport.setDescription("This is a test report");

        Mockito.when(reportRepository.save(Mockito.any())).thenReturn(testReport);
        Mockito.when(reportRepository.findByReportId(7L)).thenReturn(testReport);
    }

    @Test
    public void createReport_validInputs_success() {

        Report createdReport = reportService.createReport(testReport);

        // then
        Mockito.verify(reportRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testReport.getReportId(), createdReport.getReportId());
        assertEquals(testReport.getSession(), createdReport.getSession());
        assertEquals(testReport.getUser(), createdReport.getUser());
        assertEquals(testReport.getReason(), createdReport.getReason());
        assertEquals(testReport.getDescription(), createdReport.getDescription());

    }

    @Test
    public void createReport_invalid_userId() {
        participant.setUserId(4L);

        Mockito.when(userRepository.findByUserId(4L)).thenReturn(null);

        Throwable thrown = assertThrows(ResponseStatusException.class, () -> reportService.createReport(testReport));

        assertEquals("404 NOT_FOUND \"User with id 4 was not found\"", thrown.getMessage());

    }

    @Test
    public void createReport_invalid_sessionId() {
        testSession.setSessionId(5L);

        Mockito.when(sessionRepository.findBySessionId(5L)).thenReturn(null);

        Throwable thrown = assertThrows(ResponseStatusException.class, () -> reportService.createReport(testReport));

        assertEquals("404 NOT_FOUND \"Session with id 5 was not found\"", thrown.getMessage());

    }

    @Test
    public void getOneReport_valid() {

        Report returnedReport = reportService.getReport(testReport.getReportId());

        assertEquals(testReport.getReportId(), returnedReport.getReportId());
        assertEquals(testReport.getSession(), returnedReport.getSession());
        assertEquals(testReport.getUser(), returnedReport.getUser());
        assertEquals(testReport.getReason(), returnedReport.getReason());
        assertEquals(testReport.getDescription(), returnedReport.getDescription());
    }

}
