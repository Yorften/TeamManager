package com.teammanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.teammanager.dto.auth.LoginRequest;
import com.teammanager.model.User;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    
    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removedAt", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    User loginRequestToUser(LoginRequest loginRequest);
    
    @Mapping(source = "username", target = "username")
    @Mapping(target = "password", ignore = true)
    LoginRequest userToLoginRequest(User user);
    
    default String encodePassword(String password) {
        return password;
    }
}
