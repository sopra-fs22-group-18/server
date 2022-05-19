package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.SessionDTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.SessionService;
import ch.uzh.ifi.hase.soprafs22.service.Socket;
import ch.uzh.ifi.hase.soprafs22.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
  private final UserService userService;
  private final Socket socket;

  SessionController(SessionService sessionService, UserService userService, Socket socket) {
    this.sessionService = sessionService;
    this.userService = userService;
    this.socket = socket;
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

    @GetMapping("/sessions/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SessionGetDTO getSession(@PathVariable Long sessionId) {

        Session session = sessionService.getSession(sessionId);
        SessionGetDTO sessionGetDTO = SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session);
        sessionGetDTO.setHostUsername(session.getHost().getUsername());

        return sessionGetDTO;
    }

    @GetMapping("/sessions/join/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SessionGetDTO joinSessionByQueue(@PathVariable Long userId) {
        Session session = sessionService.joinSessionByQueue(userId);
        SessionGetDTO sessionGetDTO = SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session);
        return sessionGetDTO;
    }

    @GetMapping("/sessions/{identifier}/join/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SessionGetDTO joinSessionByIdentifier(@PathVariable String identifier, @PathVariable Long userId) {
        Session session = sessionService.joinSessionByIdentifier(userId, identifier);
        SessionGetDTO sessionGetDTO = SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session);
        return sessionGetDTO;
    }

    @PutMapping("/sessions/{sessionId}/leave/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SessionGetDTO leaveSession(@PathVariable Long sessionId, @PathVariable Long userId) {
        Session session = sessionService.removeParticipant(sessionId, userId);
        SessionGetDTO sessionGetDTO = SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session);
        return sessionGetDTO;
    }

  @PostMapping("/sessions/{sessionId}/close/{winnerId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void declareSessionWinner(@PathVariable Long sessionId, @PathVariable Long winnerId) throws IOException {
    User winner = userService.getUser(winnerId);
    socket.closeSession(sessionId, "User : " + winner.getUsername() + " won, session closes");
  }

  @PostMapping("/sessions/{sessionId}/close")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void closeSessionByHost(@PathVariable Long sessionId) throws IOException {
    socket.closeSession(sessionId, "The host has closed the session");
  }

  @GetMapping("/sessions/{userId}/posts")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SessionGetDTO> getallPosts(@PathVariable Long userId) {
    // fetch all sessions in the internal representation
    List<Session> activeSessions = sessionService.getAllSessions();
    List<SessionGetDTO> sessionGetDTOs = new ArrayList<>();

    // convert each session to the API represent ation
    for (Session session : activeSessions) {
      if(session.getHost().getUserId()==userId)
      sessionGetDTOs.add(SessionDTOMapper.INSTANCE.convertEntityToSessionGetDTO(session));
    }
    return sessionGetDTOs;
  }
}


