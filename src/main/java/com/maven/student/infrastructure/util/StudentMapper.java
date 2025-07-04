package com.maven.student.infrastructure.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.maven.student.domain.model.StudentEntity;
import com.openapi.generate.model.RequestStudentDto;
import com.openapi.generate.model.ResponseStudentDto;

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
        @Mapping(source = "document", target = "document"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "age", target = "age")
    })
    StudentEntity requestToStudent(RequestStudentDto request);

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
    ResponseStudentDto studentToResponse(StudentEntity student);

}
