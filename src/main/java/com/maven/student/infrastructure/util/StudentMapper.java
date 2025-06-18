package com.maven.student.infrastructure.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.maven.student.application.dto.RequestDto;
import com.maven.student.application.dto.ResponseDto;
import com.maven.student.domain.model.StudentEntity;

/**
 * StudentMapper.
 * This interface defines methods to map between RequestDto, ResponseDto, and StudentEntity.
 * It uses MapStruct for the mapping implementation.
 *
 * @author Joseph Magallanes
 * @since 2025-06-16
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {

    /**
     * Maps a RequestDto to a StudentEntity.
     * @param request the RequestDto to map
     * @return the mapped StudentEntity
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "document", target = "document"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "age", target = "age")
    })
    StudentEntity requestToStudent(RequestDto request);

    /**
     * Maps a StudentEntity to a ResponseDto.
     * @param student the StudentEntity to map
     * @return the mapped ResponseDto
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "document", target = "document"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "age", target = "age")
    })
    ResponseDto studentToResponse(StudentEntity student);

}
