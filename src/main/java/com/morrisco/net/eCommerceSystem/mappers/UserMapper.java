package com.morrisco.net.eCommerceSystem.mappers;


import com.morrisco.net.eCommerceSystem.dtos.RegisterUserRequest;
import com.morrisco.net.eCommerceSystem.dtos.UpdateUserRequest;
import com.morrisco.net.eCommerceSystem.dtos.UserDto;
import com.morrisco.net.eCommerceSystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {

    UserDto toDto (User user);
    User toEntity(RegisterUserRequest registerUserRequest);
    void updateUser(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
