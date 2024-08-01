package org.rdutta.jwt_access_demo.service;

import org.rdutta.jwt_access_demo.dto.LoginUserDto;
import org.rdutta.jwt_access_demo.entity.Users;
import org.rdutta.jwt_access_demo.exceptions.UserExceptions;
import org.rdutta.jwt_access_demo.repository.UsersRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UsersRepository userRepository;
    
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UsersRepository userRepository,
        AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }
    public Users authenticate(LoginUserDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()-> new UserExceptions("User email is not exist, Please register yourself before"));
    }
}
