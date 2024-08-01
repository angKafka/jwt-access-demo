package org.rdutta.jwt_access_demo.mapper;

import java.time.Instant;
import java.util.Date;

import org.rdutta.jwt_access_demo.dto.UsersDto;
import org.rdutta.jwt_access_demo.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {
     private final PasswordEncoder passwordEncoder;


    @Autowired
    public UsersMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public Users toUsers(UsersDto usersDto){
        return Users.builder()
        .username(usersDto.getUsername())
        .email(usersDto.getEmail())
        .password(passwordEncoder.encode(usersDto.getPassword()))
        .userCreatedAt(Date.from(Instant.now()))
        .userModifiedAt(usersDto.getUserModifiedAt())
        .build();
    }
}
