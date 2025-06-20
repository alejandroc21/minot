package com.alejandroct.minot_api.config.jwt;

import com.alejandroct.minot_api.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setup(){
        ReflectionTestUtils.setField(jwtService, "secretKey", Base64.getEncoder().encodeToString("15291f67d99ea7bc578c3544dadfbb991e66fa69cb36ff70fe30e798e111ff5f".getBytes()));
        ReflectionTestUtils.setField(jwtService, "tokenExpiration", 3600000L);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 7200000L);
    }

    private final User testUser = User.builder()
            .name("testUser")
            .email("test@test.com")
            .password("test123")
            .build();

    @Test
    void generateToken() {
        String token = this.jwtService.generateToken(testUser);
        assertNotNull(token);
        String email = this.jwtService.extractUsername(token);
        assertEquals(testUser.getEmail(), email);
    }

    @Test
    void generateRefreshToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String refreshToken = this.jwtService.generateRefreshToken(testUser);
        String email = jwtService.extractUsername(refreshToken);
        assertEquals(testUser.getEmail(), email);

        Method method = JwtService.class.getDeclaredMethod("extractAllClaims", String.class);
        method.setAccessible(true);
        Claims claims = (Claims) method.invoke(jwtService, refreshToken);
        assertEquals("refresh", claims.get("type"));
    }

    @Test
    void isTokenExpired() {
        String token = this.jwtService.generateToken(testUser);
        assertFalse(this.jwtService.isTokenExpired(token));
    }

    @Test
    void isTokenValid() {
        String token = this.jwtService.generateToken(testUser);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@test.com")
                .password("false")
                .build();
        assertTrue(this.jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void setRefreshTokenCookie() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        jwtService.setRefreshTokenCookie(response, testUser);
        String setCookieHeader = response.getHeader(HttpHeaders.SET_COOKIE);
        assertNotNull(setCookieHeader);
        assertTrue(setCookieHeader.contains("refreshToken="));
        assertTrue(setCookieHeader.contains("HttpOnly"));
        assertTrue(setCookieHeader.contains("Path=/api/auth/refresh"));
    }
}