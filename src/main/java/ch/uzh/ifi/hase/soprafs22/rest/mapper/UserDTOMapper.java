package ch.uzh.ifi.hase.soprafs22.rest.mapper;

import ch.uzh.ifi.hase.soprafs22.entity.User;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.UserPutDTO;

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
public interface UserDTOMapper {

  UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);
  //converting UserPostDTO to Entity
  @Mapping(source = "password", target = "password")
  @Mapping(source = "username", target = "username")
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);
  
  //mapping internal representation of  User to UserGetDTO
  @Mapping(source = "userId", target = "userId")
  @Mapping(source = "username", target = "username")
  UserGetDTO convertEntityToUserGetDTO(User user);

  //mapping internal representation of  User to UserPutDTO
  @Mapping(source = "userId", target = "userId")
  @Mapping(source = "username", target = "username")
  User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);
 
}
