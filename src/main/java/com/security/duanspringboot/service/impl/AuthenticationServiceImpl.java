package com.security.duanspringboot.service.impl;

import com.security.duanspringboot.dto.request.RefreshTokenRequest;
import com.security.duanspringboot.dto.request.SignInRequest;
import com.security.duanspringboot.dto.request.SignUpRequest;
import com.security.duanspringboot.dto.response.JwtAuthenticationResponse;
import com.security.duanspringboot.entity.UserModel;
import com.security.duanspringboot.entity.UserRoleModel;
import com.security.duanspringboot.repository.UserRepository;
import com.security.duanspringboot.service.AuthenticationService;
import com.security.duanspringboot.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public UserModel signup(SignUpRequest signUpRequest) {
        UserModel userModel = new UserModel();

        userModel.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
        userModel.setEmail(signUpRequest.getEmail());
        userModel.setFirstName(signUpRequest.getFirstName());
        userModel.setLastName(signUpRequest.getLastName());
        userModel.setUserRoleModel(UserRoleModel.USER);
        userModel.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(userModel);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email or password")
        );
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserModel userModel = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), userModel)) {
            var jwt = jwtService.generateToken(userModel);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
