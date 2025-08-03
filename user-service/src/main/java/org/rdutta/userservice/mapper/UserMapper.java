package org.rdutta.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rdutta.commonlibrary.dto.UserRequestDTO;
import org.rdutta.userservice.entity.PSUser;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "active", constant = "true")
    PSUser toEntity(UserRequestDTO dto);
}
