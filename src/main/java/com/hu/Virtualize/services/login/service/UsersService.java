package com.hu.Virtualize.services.login.service;

import com.hu.Virtualize.entities.AdminEntity;
import com.hu.Virtualize.entities.UserEntity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.Set;

public interface UsersService {
    UserEntity addUser(UserEntity userEntity);
    AdminEntity addAdmin(AdminEntity adminEntity);
}
