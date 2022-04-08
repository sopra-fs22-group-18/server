package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.CommentPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.CommentDTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/sessions/{sessionId}/comments")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CommentGetDTO> getAllSessionComments(@PathVariable Long sessionId) {
        // fetch all sessions in the internal representation
        List<Comment> sessionComments = commentService.getSessionComments(sessionId);
        List<CommentGetDTO> commentGetDTOs = new ArrayList<>();

        // convert each session to the API representation
        for (Comment comment : sessionComments) {
            commentGetDTOs.add(CommentDTOMapper.INSTANCE.convertEntityToCommentGetDTO(comment));
        }
        return commentGetDTOs;

    }

    @PostMapping("/sessions/{sessionId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public CommentGetDTO createSessionComment(@RequestBody CommentPostDTO commentPostDTO) {
        // convert API session to internal representation
        Comment commentInput = CommentDTOMapper.INSTANCE.convertCommentPostDTOtoEntity(commentPostDTO);

        // create Session
        Comment createdSessionComment = commentService.createSessionComment(commentInput);

        // convert internal representation of session back to API
        return CommentDTOMapper.INSTANCE.convertEntityToCommentGetDTO(createdSessionComment);
    }

}
