package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.constant.SessionStatus;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  public void givenSessions_whenGetSessions_thenReturnJsonArray() throws Exception {
    // given
    User host = new User();
    host.setUsername("host");
    host.setUserId(1L);

    Session session = new Session();
    session.setHost(host);
    session.setMaxParticipants(2);
    session.setTitle("testSession");
    session.setStatus(SessionStatus.CREATED);


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
        .andExpect(jsonPath("$[0].status", is(session.getStatus().toString())));
  }



  @Test
  public void createSession_validInput_sessionCreated() throws Exception {
    // given
    User host = new User();
    host.setUsername("host");
    host.setUserId(1L);

    Session session = new Session();
    session.setHost(host);
    session.setMaxParticipants(2);
    session.setTitle("testSession");
    session.setStatus(SessionStatus.CREATED);
    session.setSessionId(1L);

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
        .andExpect(jsonPath("$.status", is(SessionStatus.CREATED.toString())));
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