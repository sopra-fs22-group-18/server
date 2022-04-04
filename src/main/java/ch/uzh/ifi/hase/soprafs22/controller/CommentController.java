package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Comment Controller
 * This class is responsible for handling all REST request that are related to
 * the session.
 * The controller will receive the request and delegate the execution to the
 * CommentService and finally return the result.
 */
@RestController
public class CommentController {

    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    /*
    @GetMapping("/users/posts/{sessionId}/comments")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommentGetDTO getAllSessionComments(@PathVariable("sessionId") Long sessionId) {
        TODO: implement the getAllSessionComments
    }
    */
}