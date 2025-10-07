package com.morrisco.net.eCommerceSystem.users;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDto toDto (User user);
    User toEntity(RegisterUserRequest registerUserRequest);
    void updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
