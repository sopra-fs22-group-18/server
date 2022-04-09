package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.Session;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.SessionPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * UserDTOMapper
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
public interface SessionDTOMapper {

  SessionDTOMapper INSTANCE = Mappers.getMapper(SessionDTOMapper.class);
  @Mapping(source = "host", target = "host")
  @Mapping(source = "maxParticipants", target = "maxParticipants")
  @Mapping(source = "title", target = "title")
  Session convertSessionPostDTOtoEntity(SessionPostDTO sessionPostDTO);

  @Mapping(source = "sessionId", target = "sessionId")
  @Mapping(source = "host", target = "host")
  @Mapping(source = "winner", target = "winner")
  @Mapping(source = "participants", target = "participants")
  @Mapping(source = "maxParticipants", target = "maxParticipants")

  @Mapping(source = "userStatus", target = "userStatus")
  @Mapping(source = "title", target = "title")
  SessionGetDTO convertEntityToSessionGetDTO(Session session);

}
