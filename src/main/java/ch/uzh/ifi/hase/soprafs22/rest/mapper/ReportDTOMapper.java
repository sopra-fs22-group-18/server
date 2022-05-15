package ch.uzh.ifi.hase.soprafs22.rest.mapper;
import ch.uzh.ifi.hase.soprafs22.entity.Report;
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
public interface ReportDTOMapper {

    ReportDTOMapper INSTANCE = Mappers.getMapper(ReportDTOMapper.class);
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "session", target = "session")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "reason", target = "reason")
    @Mapping(source = "createdDate", target = "createdDate")
    Report convertReportPostDTOtoEntity(ReportPostDTO reportPostDTO);

    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "session", target = "session")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "reason", target = "reason")
    @Mapping(source = "createdDate", target = "createdDate")
    ReportGetDTO convertEntityToReportGetDTO(Report report);

}