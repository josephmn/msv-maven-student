package com.maven.student.infrastructure.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.maven.student.domain.model.TeacherEntity;
import com.openapi.generate.model.RequestTeacherDto;
import com.openapi.generate.model.ResponseTeacherDto;

/**
 * TeacherMapper.
 * This interface defines methods to map between RequestDto, ResponseDto, and StudentEntity.
 * It uses MapStruct for the mapping implementation.
 *
 * @author Joseph Magallanes
 * @since 2025-06-25
 */
@Mapper(componentModel = "spring")
public interface TeacherMapper {

    /**
     * Maps a RequestTeacherDto to a TeacherEntity.
     * @param request the RequestTeacherDto to map
     * @return the mapped TeacherEntity
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "document", target = "document"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "age", target = "age")
    })
    TeacherEntity requestToTeacher(RequestTeacherDto request);

    /**
     * Maps a TeacherEntity to a ResponseTeacherDto.
     * @param student the TeacherEntity to map
     * @return the mapped ResponseTeacherDto
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "document", target = "document"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "age", target = "age")
    })
    ResponseTeacherDto teacherToResponse(TeacherEntity student);

}
