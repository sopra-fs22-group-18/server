package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.ReportReason;
import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentPostDTO;

import ch.uzh.ifi.hase.soprafs22.rest.dto.ReportPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.CommentService;
import ch.uzh.ifi.hase.soprafs22.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ReportControllerTest
 * This is a WebMvcTest which allows to test the ReportController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the ReportController works.
 */
@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    private final User host = new User();
    private final User participant1 = new User();
    private final User participant2 = new User();
    private final Set<User> participants = new HashSet<>();
    private final Session session = new Session();
    private final Comment comment = new Comment();
    private final CommentPostDTO commentPostDTO = new CommentPostDTO();
    private final Report report = new Report();
    private final ReportPostDTO reportPostDTO = new ReportPostDTO();

    @BeforeEach
    private void givens(){
        host.setUsername("host");
        host.setUserId(1L);

        participant1.setUsername("participant1");
        participant1.setUserId(2L);

        participant2.setUsername("participant2");
        participant2.setUserId(3L);

        participants.add(participant1);
        participants.add(participant2);

        session.setHost(host);
        session.setMaxParticipants(2);
        session.setParticipants(participants);
        session.setTitle("testSession");
        session.setSessionStatus(SessionStatus.CREATED);
        session.setSessionId(1L);

        comment.setCommentId(5L);
        comment.setSession(session);
        comment.setUser(participant1);
        comment.setCommentText("This is a test comment");

        report.setReportId(2L);
        report.setSession(session);
        report.setComment(comment);
        report.setUser(participant1);
        report.setReason(ReportReason.THREAT);
        report.setDescription("This is a test report");

        reportPostDTO.setUser(participant1);
        reportPostDTO.setDescription("This is a test report");
        reportPostDTO.setSession(session);
        reportPostDTO.setComment(comment);
        reportPostDTO.setReason(ReportReason.THREAT);
    }

    @Test
    void givenSessionReport_whenGetSessionReports_thenReturnJsonArray() throws Exception {
        List<Report> allSessionReports = Collections.singletonList(report);

        given(reportService.getSessionReports(1L)).willReturn(allSessionReports);

        // when
        MockHttpServletRequestBuilder getRequest = get("/sessions/" + 1 +"/reports")
                .contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].session.sessionId", is(session.getSessionId().intValue())))
                .andExpect(jsonPath("$[0].reportId", is(report.getReportId().intValue())))
                .andExpect(jsonPath("$[0].user.userId", is(participant1.getUserId().intValue())))
                .andExpect(jsonPath("$[0].comment.commentId", is(comment.getCommentId().intValue())))
                .andExpect(jsonPath("$[0].reason", is(report.getReason().toString())))
                .andExpect(jsonPath("$[0].description", is(report.getDescription())));
    }

    @Test
    void createSessionReport_validInput_sessionReportCreated() throws Exception {
        given(reportService.createReport(Mockito.any())).willReturn(report);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/sessions/1L/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reportPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reportId", is(report.getReportId().intValue())))
                .andExpect(jsonPath("$.user.userId", is(participant1.getUserId().intValue())))
                .andExpect(jsonPath("$.session.sessionId", is(session.getSessionId().intValue())))
                .andExpect(jsonPath("$.comment.commentId", is(comment.getCommentId().intValue())))
                .andExpect(jsonPath("$.reason", is(report.getReason().toString())))
                .andExpect(jsonPath("$.description", is(report.getDescription())));
    }

    /**
     * Helper Method to convert DTO into a JSON string such that the input
     * can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     *
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }

}