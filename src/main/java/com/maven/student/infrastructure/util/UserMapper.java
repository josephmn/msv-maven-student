package com.maven.student.infrastructure.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.maven.student.domain.model.UserEntity;
import com.openapi.generate.model.ResponseUserDto;

/**
 * UserMapper.
 * This interface defines methods to map between ResponseUserDto and UserEntity.
 * It uses MapStruct for the mapping implementation.
 *
 * @author Joseph Magallanes
 * @since 2025-07-178
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a ResponseUserDto to a UserEntity.
     * @param request the ResponseUserDto to map
     * @return the mapped UserEntity
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "username", target = "username"),
        @Mapping(source = "password", target = "password"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "role", target = "role"),
        @Mapping(source = "enabled", target = "enabled")
    })
    UserEntity requestToUser(ResponseUserDto request);

    /**
     * Maps a UserEntity to a ResponseUserDto.
     * @param user the UserEntity to map
     * @return the mapped ResponseUserDto
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "username", target = "username"),
        @Mapping(source = "password", target = "password"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "role", target = "role"),
        @Mapping(source = "enabled", target = "enabled")
    })
    ResponseUserDto userToResponse(UserEntity user);
}
