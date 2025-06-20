package com.alejandroct.minot_api.auth.controller;

import com.alejandroct.minot_api.auth.dto.LoginRequest;
import com.alejandroct.minot_api.auth.dto.RegisterRequest;
import com.alejandroct.minot_api.auth.dto.TokenResponse;
import com.alejandroct.minot_api.auth.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody LoginRequest request, HttpServletResponse response){
        return ResponseEntity.ok(this.authService.authenticate(request, response));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response){
        return ResponseEntity.ok(this.authService.register(request, response));
    }

    @PostMapping("/google-login")
    public ResponseEntity<TokenResponse> googleAuthenticate(@RequestBody TokenResponse tokenID, HttpServletResponse response){
        return ResponseEntity.ok(this.authService.googleAuthenticate(tokenID, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response){
        return ResponseEntity.ok(this.authService.refreshToken(refreshToken, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logOut(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(true);
    }
}
