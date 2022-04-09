package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentPostDTO;

import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.CommentService;
import ch.uzh.ifi.hase.soprafs22.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * CommentControllerTest
 * This is a WebMvcTest which allows to test the CommentController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the CommentController works.
 */
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;
    /* TODO: Fix this test givenSessions_whenGetSessions_thenReturnJsonArray()
    @Test
    void givenSessionComment_whenGetSessionComments_thenReturnJsonArray() throws Exception {
        // given
        User host = new User();
        host.setUsername("host");
        host.setUserId(1L);

        User participant1 = new User();
        host.setUsername("participant1");
        host.setUserId(2L);

        User participant2 = new User();
        host.setUsername("participant2");
        host.setUserId(3L);

        Set<User> participants = new HashSet<>();
        participants.add(participant1);
        participants.add(participant2);

        Session session = new Session();
        session.setHost(host);
        session.setMaxParticipants(2);
        session.setParticipants(participants);
        session.setTitle("testSession");
        session.setStatus(SessionStatus.CREATED);
        session.setSessionId(1L);

        Comment comment = new Comment();
        comment.setCommentId(5L);
        comment.setSession(session);
        comment.setUser(participant1);
        comment.setCommentText("This is a test comment");


        List<Comment> allSessionComments = Collections.singletonList(comment);

        given(commentService.getSessionComments(1L)).willReturn(allSessionComments);

        // when
        MockHttpServletRequestBuilder getRequest = get("/sessions/1L/comments")
                .contentType(MediaType.APPLICATION_JSON);
        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].participant1.userId", is(comment.getUser().getUserId().intValue())))
                .andExpect(jsonPath("$[0].session.sessionId", is(session.getSessionId().intValue())))
                .andExpect(jsonPath("$[0].commentText", is(comment.getCommentText())));
    }
    */

    @Test
    void createSessionComment_validInput_sessionCommentCreated() throws Exception {
        // given
        User host = new User();
        host.setUsername("host");
        host.setUserId(1L);

        User participant1 = new User();
        host.setUsername("participant1");
        host.setUserId(2L);

        User participant2 = new User();
        host.setUsername("participant2");
        host.setUserId(3L);

        Set<User> participants = new HashSet<>();
        participants.add(participant1);
        participants.add(participant2);

        Session session = new Session();
        session.setHost(host);
        session.setMaxParticipants(2);
        session.setParticipants(participants);
        session.setTitle("testSession");
        session.setStatus(SessionStatus.CREATED);
        session.setSessionId(1L);

        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setSession(session);
        comment.setUser(participant1);
        comment.setCommentText("This is a test comment");

        CommentPostDTO commentPostDTO = new CommentPostDTO();
        commentPostDTO.setUser(participant1);
        commentPostDTO.setCommentText("This is a test comment");
        commentPostDTO.setSession(session);


        given(commentService.createSessionComment(Mockito.any())).willReturn(comment);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/sessions/1L/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(commentPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId", is(comment.getCommentId().intValue())))
                .andExpect(jsonPath("$.user.userId", is(participant1.getUserId())))
                .andExpect(jsonPath("$.session.sessionId", is(session.getSessionId().intValue())))
                .andExpect(jsonPath("$.commentText", is(comment.getCommentText())));
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
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}