package com.greentracer.app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.greentracer.app.utils.JwtTokenUtil.JWT_TOKEN_VALIDITY;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;
    private String secret = "testSecret";

    @BeforeEach
    public void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", secret);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtTokenUtil.generateToken("testUser");
        assertNotNull(token);
    }

    @Test
    public void testGetUsernameFromToken() {
        Map<String, Object> claims = new HashMap<>();
        String subject = "testUser";
        String token = generateToken(claims, subject);
        assertEquals(subject, jwtTokenUtil.getUsernameFromToken(token));
    }

    @Test
    public void testValidateToken() {
        Map<String, Object> claims = new HashMap<>();
        String subject = "testUser";
        String token = generateToken(claims, subject);
        assertTrue(jwtTokenUtil.validateToken(token, subject));
    }

    @Test
    public void testIsTokenExpired() {
        Map<String, Object> claims = new HashMap<>();
        String subject = "testUser";
        String token = generateToken(claims, subject);
        assertFalse(jwtTokenUtil.getExpirationDateFromToken(token).before(new Date()));
    }


    @Test
    public void testGetClaimFromToken() {
        String token = jwtTokenUtil.generateToken("testUser");
        assertEquals("testUser", jwtTokenUtil.getClaimFromToken(token, Claims::getSubject));
    }





    //redéfinition de la méthode generateToken pour les besoins des tests
    private String generateToken(Map<String, Object> claims, String subject) {
        String encodedString = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, encodedString).compact();
    }

}