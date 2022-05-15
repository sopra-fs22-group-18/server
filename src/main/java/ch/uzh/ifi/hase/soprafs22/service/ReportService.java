package ch.uzh.ifi.hase.soprafs22.service;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.repository.CommentRepository;
import ch.uzh.ifi.hase.soprafs22.repository.ReportRepository;
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

import java.util.Date;
import java.util.List;


/**
 * Report Service
 * This class is the "worker" and responsible for all functionality related to
 * the session
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */

@Service
@Transactional
public class ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportService.class);
    private final CommentRepository commentRepository;
    private final SessionRepository sessionRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    @Autowired
    public ReportService(@Qualifier("commentRepository") CommentRepository commentRepository,
                          @Qualifier("sessionRepository") SessionRepository sessionRepository,
                          @Qualifier("reportRepository") ReportRepository reportRepository,
                         @Qualifier("userRepository") UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.sessionRepository = sessionRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }


    public List<Report> getSessionReports(Long sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session != null) {
            List<Report> sessionReports = this.reportRepository.findBySession(session);

            return sessionReports;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There exists no session under this sessionId!");
        }
    }

    public Report getReport(Long reportId) {
        return this.reportRepository.findByReportId(reportId);
    }

    public Report createReport(Report newReport) {

        // find user
        String userErrorMessage = "User with id %x was not found";
        Long userId = newReport.getUser().getUserId();
        User user = userRepository.findByUserId(userId);

        // find session
        String sessionErrorMessage = "Session with id %x was not found";
        Long sessionId = newReport.getSession().getSessionId();
        Session session = sessionRepository.findBySessionId(sessionId);

        // find comment
        String commentErrorMessage = "Comment with id %x was not found";
        Long commentId = newReport.getComment().getCommentId();
        Comment comment = commentRepository.findByCommentId(commentId);

        if (session == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(sessionErrorMessage,sessionId));
        } else if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(commentErrorMessage,commentId));
        } else if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(userErrorMessage,userId));
        } else {
            // set createdDate, session and comment
            newReport.setCreatedDate(new java.util.Date());
            newReport.setSession(session);
            newReport.setComment(comment);

            // save to repo and flush
            newReport = reportRepository.save(newReport);
            reportRepository.flush();

            log.debug("Created Information for Report: {}", newReport);
            return newReport;
        }

    }



}