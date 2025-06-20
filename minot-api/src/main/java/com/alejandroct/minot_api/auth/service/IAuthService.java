package com.alejandroct.minot_api.auth.service;

import com.alejandroct.minot_api.auth.dto.LoginRequest;
import com.alejandroct.minot_api.auth.dto.RegisterRequest;
import com.alejandroct.minot_api.auth.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    TokenResponse authenticate(LoginRequest request, HttpServletResponse response);

    TokenResponse register(RegisterRequest request, HttpServletResponse response);

    TokenResponse googleAuthenticate(TokenResponse tokenID, HttpServletResponse response);

    TokenResponse refreshToken(String refreshToken, HttpServletResponse response);
}
