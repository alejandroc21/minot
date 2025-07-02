package com.alejandroct.minot_api.auth.service.impl;

import com.alejandroct.minot_api.auth.dto.LoginRequest;
import com.alejandroct.minot_api.auth.dto.RegisterRequest;
import com.alejandroct.minot_api.auth.dto.TokenResponse;
import com.alejandroct.minot_api.auth.service.IAuthService;
import com.alejandroct.minot_api.config.jwt.JwtService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import com.google.api.client.http.javanet.NetHttpTransport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

import static com.alejandroct.minot_api.constants.ErrorMessageConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final JwtService jwtService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    @Value("${google.client.id}")
    private String GoogleClientId;

    @Override
    public TokenResponse authenticate(LoginRequest request, HttpServletResponse response) {
        this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        User user = this.userService.findUserByEmail(request.email());
        String token = this.jwtService.generateToken(user);
        this.jwtService.setRefreshTokenCookie(response, user);
        return new TokenResponse(token);
    }

    @Override
    public TokenResponse register(RegisterRequest request, HttpServletResponse response) {
        if(this.userService.userExists(request.email())){
            throw new IllegalArgumentException(EMAIL_IN_USE);
        }
        User user = User.builder()
                .email(request.email())
                .password(this.passwordEncoder.encode(request.password()))
                .build();

        String token = this.jwtService.generateToken(this.userService.save(user));
        this.jwtService.setRefreshTokenCookie(response, user);
        return new TokenResponse(token);
    }

    @Override
    public TokenResponse googleAuthenticate(TokenResponse tokenID, HttpServletResponse response) {
        if(tokenID.token() == null){
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(this.GoogleClientId))
                .build();

        try {
            GoogleIdToken googleToken = verifier.verify(tokenID.token());
            if(googleToken == null){
                throw new IllegalArgumentException(INVALID_TOKEN);
            }

            Payload payload = googleToken.getPayload();
            String email = payload.getEmail();
            User user;
            if(this.userService.userExists(email)){
                user = this.userService.findUserByEmail(email);
            }else{
                String name = payload.get("name").toString();
                String password = UUID.randomUUID().toString();
                user = User.builder()
                        .email(email)
                        .password(this.passwordEncoder.encode(password))
                        .build();
                this.userService.save(user);
            }

            String token = this.jwtService.generateToken(user);
            this.jwtService.setRefreshTokenCookie(response, user);
            return new TokenResponse(token);

        } catch (GeneralSecurityException | IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
    }

    @Override
    public TokenResponse refreshToken(String refreshToken, HttpServletResponse response) {
        if(refreshToken == null){
            throw new IllegalArgumentException(NO_REFRESH_TOKEN);
        }
        String username;
        try{
            username = this.jwtService.extractUsername(refreshToken);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new IllegalArgumentException(INVALID_TOKEN);
        }
        User user = this.userService.findUserByEmail(username);

        if(!this.jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException(INVALID_EXPIRED_TOKEN);
        }

        String token = this.jwtService.generateToken(user);
        this.jwtService.setRefreshTokenCookie(response, user);
        return new TokenResponse(token);
    }
}
