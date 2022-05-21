package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.service.SessionService;
import ch.uzh.ifi.hase.soprafs22.service.Socket;
import ch.uzh.ifi.hase.soprafs22.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SessionControllerTest
 * This is a WebMvcTest which allows to test the SessionController i.e. GET/POST
 * request without actually sending them over the network.
 * This tests if the SessionController works.
 */
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private UserService userService;

  @MockBean
  private Socket socket;

  private final User host = new User();
  private final Session session = new Session();
  private final User participant1 = new User();
  private final Set<User> participants = new HashSet<>();
  private final Set<User> emptyList = new HashSet<>();

  @BeforeEach
  public void createUserAndSession(){
      host.setUsername("host");
      host.setUserId(1L);

      participant1.setUsername("participant1");
      participant1.setUserId(2L);

      participants.add(participant1);

      session.setHost(host);
      session.setSessionId(3L);
      session.setMaxParticipants(2);
      session.setTitle("testSession");
      session.setImageUrl("testURL");
      session.setParticipants(participants);
      session.setSessionStatus(SessionStatus.CREATED);
  }

  @Test
  public void givenSessions_whenGetSessions_thenReturnJsonArray() throws Exception {
    // additional given
    List<Session> allSessions = Collections.singletonList(session);

    // this mocks the SessionService -> we define above what the sessionService should
    // return when getActiveSessions() is called
    given(sessionService.getActiveSessions()).willReturn(allSessions);

    // when
    MockHttpServletRequestBuilder getRequest = get("/sessions").contentType(MediaType.APPLICATION_JSON);

    // then
    mockMvc.perform(getRequest).andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].host.userId", is(session.getHost().getUserId().intValue())))
        .andExpect(jsonPath("$[0].maxParticipants", is(session.getMaxParticipants())))
        .andExpect(jsonPath("$[0].title", is(session.getTitle())))
        .andExpect(jsonPath("$[0].sessionStatus", is(session.getSessionStatus().toString())))
        .andExpect(jsonPath("$[0].imageUrl", is(session.getImageUrl())));
  }

  @Test
  public void givenSession_whenGetSession_thenReturnJsonArray() throws Exception {
      // this mocks the SessionService -> we define above what the sessionService should
      // return when getActiveSessions() is called
      given(sessionService.getSession(session.getSessionId())).willReturn(session);

      // when
      MockHttpServletRequestBuilder getRequest = get("/sessions/" + 3).contentType(MediaType.APPLICATION_JSON);

      // then
      mockMvc.perform(getRequest).andExpect(status().isOk())
              .andExpect(jsonPath("$.sessionId", is(session.getSessionId().intValue())))
              .andExpect(jsonPath("$.host.userId", is(session.getHost().getUserId().intValue())))
              .andExpect(jsonPath("$.maxParticipants", is(session.getMaxParticipants())))
              .andExpect(jsonPath("$.title", is(session.getTitle())))
              .andExpect(jsonPath("$.sessionStatus", is(session.getSessionStatus().toString())))
              .andExpect(jsonPath("$.imageUrl", is(session.getImageUrl())));


  }

  @Test
  public void createSession_validInput_sessionCreated() throws Exception {
    // given
    SessionPostDTO sessionPostDTO = new SessionPostDTO();
    sessionPostDTO.setHost(host);
    sessionPostDTO.setMaxParticipants(2);
    sessionPostDTO.setTitle("testSession");

    given(sessionService.createSession(Mockito.any())).willReturn(session);

    // when/then -> do the request + validate the result
    MockHttpServletRequestBuilder postRequest = post("/sessions")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(sessionPostDTO));

    // then
    mockMvc.perform(postRequest)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.sessionId", is(session.getSessionId().intValue())))
        .andExpect(jsonPath("$.host.userId", is(host.getUserId().intValue())))
        .andExpect(jsonPath("$.maxParticipants", is(session.getMaxParticipants())))
        .andExpect(jsonPath("$.title", is(session.getTitle())))
        .andExpect(jsonPath("$.sessionStatus", is(SessionStatus.CREATED.toString())))
        .andExpect(jsonPath("$.imageUrl", is(session.getImageUrl())));
  }

    @Test
    public void givenSession_whenParticipantJoinsSession_thenReturnJsonArray() throws Exception {
      session.setParticipants(emptyList);
        List<Session> allSessions = Collections.singletonList(session);

        // this mocks the SessionService -> we define above what the sessionService should
        // return when getActiveSessions() is called
        given(sessionService.getActiveSessions()).willReturn(allSessions);

        given(sessionService.joinSessionByQueue(2L)).willReturn(session);

        // when
        MockHttpServletRequestBuilder getRequest = get("/sessions/join/" + 2).contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.maxParticipants", is(session.getMaxParticipants())))
                .andExpect(jsonPath("$.title", is(session.getTitle())))
                .andExpect(jsonPath("$.sessionStatus", is(session.getSessionStatus().toString())))
                .andExpect(jsonPath("$.imageUrl", is(session.getImageUrl())))
                .andExpect(jsonPath("$.participants", hasSize(0)));
    }

    @Test
    public void givenSession_whenParticipantLeavesSession_thenReturnJsonArray() throws Exception {
        given(sessionService.removeParticipant(3L, 2L)).willReturn(session);

        MockHttpServletRequestBuilder putRequest = put("/sessions/"+ 3 +"/leave/" + 2)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(putRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId", is(session.getSessionId().intValue())))
                .andExpect(jsonPath("$.maxParticipants", is(session.getMaxParticipants())))
                .andExpect(jsonPath("$.title", is(session.getTitle())))
                .andExpect(jsonPath("$.sessionStatus", is(session.getSessionStatus().toString())))
                .andExpect(jsonPath("$.imageUrl", is(session.getImageUrl())))
                .andExpect(jsonPath("$.participants", hasSize(1)));
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
          String.format("The request body could not be created.%s", e));
    }
  }
}