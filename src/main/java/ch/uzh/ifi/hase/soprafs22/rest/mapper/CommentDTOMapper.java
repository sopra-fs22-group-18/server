package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Comment;


import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;

import ch.uzh.ifi.hase.soprafs22.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


import java.util.Date;


/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */

@Mapper
public interface CommentDTOMapper {

    CommentDTOMapper INSTANCE = Mappers.getMapper(CommentDTOMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "session", target = "session")
    @Mapping(source = "commentText", target = "commentText")
    @Mapping(source = "createdDate", target = "createdDate")
    Comment convertCommentPostDTOtoEntity(CommentPostDTO commentPostDTO);

    @Mapping(source = "commentId", target = "commentId")
    @Mapping(source = "session", target = "session")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "commentText", target = "commentText")
    @Mapping(source = "createdDate", target = "createdDate")
    CommentGetDTO convertEntityToCommentGetDTO(Comment comment);

}