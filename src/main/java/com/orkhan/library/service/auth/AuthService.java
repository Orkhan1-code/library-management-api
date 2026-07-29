package com.orkhan.library.service.auth;

import com.orkhan.library.dto.auth.JwtResponse;
import com.orkhan.library.dto.auth.LoginRequest;
import com.orkhan.library.dto.auth.RegisterRequest;

public interface AuthService {

    JwtResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);
}