package com.security.duanspringboot.service;

import com.security.duanspringboot.dto.request.RefreshTokenRequest;
import com.security.duanspringboot.dto.request.SignInRequest;
import com.security.duanspringboot.dto.request.SignUpRequest;
import com.security.duanspringboot.dto.response.JwtAuthenticationResponse;
import com.security.duanspringboot.entity.UserModel;

public interface AuthenticationService {

    UserModel signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
