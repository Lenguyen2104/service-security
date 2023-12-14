package com.security.duanspringboot.service;

import com.security.duanspringboot.dto.request.SignUpRequest;
import com.security.duanspringboot.entity.UserModel;

public interface AuthenticationService {

    UserModel signup(SignUpRequest signUpRequest);
}
