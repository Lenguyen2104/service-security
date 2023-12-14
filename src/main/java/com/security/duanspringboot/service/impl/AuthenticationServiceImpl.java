package com.security.duanspringboot.service.impl;

import com.security.duanspringboot.dto.request.SignUpRequest;
import com.security.duanspringboot.entity.UserModel;
import com.security.duanspringboot.entity.UserRoleModel;
import com.security.duanspringboot.repository.UserRepository;
import com.security.duanspringboot.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

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
}
