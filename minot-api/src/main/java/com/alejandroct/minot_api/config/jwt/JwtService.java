package com.alejandroct.minot_api.config.jwt;

import com.alejandroct.minot_api.user.model.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${token.expiration}")
    private long tokenExpiration;

    @Value("${refresh.expiration}")
    private long refreshExpiration;

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+this.tokenExpiration))
                .signWith(this.getSecretKey())
                .compact();
    }

    public String generateRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+this.refreshExpiration))
                .signWith(this.getSecretKey())
                .compact();
    }

    public String extractUsername(String token){
        return this.extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return this.extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user){
        String username = this.extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public void setRefreshTokenCookie(HttpServletResponse response, User user){
        String refreshToken = this.generateRefreshToken(user);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(this.refreshExpiration)
                .sameSite("strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(this.getSecretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(this.extractAllClaims(token));
    }

}
