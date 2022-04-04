package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.SessionDTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Session Controller
 * This class is responsible for handling all REST request that are related to
 * the session.
 * The controller will receive the request and delegate the execution to the
 * SessionService and finally return the result.
 */

@RestController
public class SessionController {

  private final SessionService sessionService;

  SessionController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @GetMapping("/sessions")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SessionGetDTO> getActiveSessions() {
    // fetch all sessions in the internal representation
    List<Session> activeSessions = sessionService.getActiveSessions();
    List<SessionGetDTO> sessionGetDTOs = new ArrayList<>();

    // convert each session to the API representation
    for (Session session : activeSessions) {
      sessionGetDTOs.add(SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session));
    }
    return sessionGetDTOs;
  }

  @PostMapping("/sessions")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public SessionGetDTO createSession(@RequestBody SessionPostDTO sessionPostDTO) {
      // convert API session to internal representation
    Session sessionInput = SessionDTOMapper.INSTANCE.convertSessionPostDTOtoEntity(sessionPostDTO);

    // create Session
    Session createdSession = sessionService.createSession(sessionInput);

    // convert internal representation of session back to API
    return SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(createdSession);
  }
}
