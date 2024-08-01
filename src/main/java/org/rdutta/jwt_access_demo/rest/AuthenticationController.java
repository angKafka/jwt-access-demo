package org.rdutta.jwt_access_demo.rest;

import org.rdutta.jwt_access_demo.configuration.auth_service.JwtService;
import org.rdutta.jwt_access_demo.dto.LoginResponse;
import org.rdutta.jwt_access_demo.dto.LoginUserDto;
import org.rdutta.jwt_access_demo.entity.Users;
import org.rdutta.jwt_access_demo.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        Users authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
        .token(jwtToken)
        .expiresIn(jwtService.getExpirationTime())
        .build();
        
        return ResponseEntity.ok(loginResponse);
    }
}
