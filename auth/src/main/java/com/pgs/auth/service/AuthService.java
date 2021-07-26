package com.pgs.auth.service;

import com.pgs.auth.payload.JwtAuthenticationResponse;
import com.pgs.auth.payload.LoginRequest;
import com.pgs.auth.payload.SignUpRequest;
import com.pgs.auth.security.UserPrincipal;

import javax.servlet.ServletRequest;

public interface AuthService {
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    void registerUser(SignUpRequest signUpRequest);

    UserPrincipal getCurrentUserInfo();

    boolean isAuthenticated(ServletRequest request);

    void activation(Long userId, String activationCode);
}
