package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

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
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

  @Mapping(source = "userId", target = "userId")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "status", target = "status")
  UserGetDTO convertEntityToUserGetDTO(User user);

  @Mapping(source = "host", target = "host")
  @Mapping(source = "winner", target = "winner")
  @Mapping(source = "participants", target = "participants")
  @Mapping(source = "maxParticipants", target = "maxParticipants")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "title", target = "title")
  Session convertSessionPostDTOtoEntity(SessionPostDTO sessionPostDTO);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "host", target = "host")
  @Mapping(source = "winner", target = "winner")
  @Mapping(source = "participants", target = "participants")
  @Mapping(source = "maxParticipants", target = "maxParticipants")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "title", target = "title")
  SessionGetDTO convertEntityToSessionGetDTO(Session session);

}
