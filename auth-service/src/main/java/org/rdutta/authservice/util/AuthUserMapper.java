package org.rdutta.authservice.util;

import org.mapstruct.Mapper;
import org.rdutta.authservice.domain.user.AuthUsersDetails;
import org.rdutta.commonlibrary.entity.Users;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUsersDetails fromUserToAuthUsersDetails(Users users);
}
