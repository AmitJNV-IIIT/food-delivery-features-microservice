//package com.ps.fooddelivery.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JwtUtilTest {
//
//    private JwtUtil jwtUtil;
//    private String secretKey = "supersecretkeywhichislongenough1234567890"; // Ensure it is long enough for the HMAC-SHA algorithm
//
//    @BeforeEach
//    public void setUp() {
//        jwtUtil = new JwtUtil();
//        ReflectionTestUtils.setField(jwtUtil, "secret", secretKey);
//    }
//
//    @Test
//    public void testGenerateToken() {
//        String username = "testuser";
//        String token = jwtUtil.generateToken(username);
//
//        assertNotNull(token);
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        assertEquals(username, claims.getSubject());
//        assertNotNull(claims.getIssuedAt());
//        assertNotNull(claims.getExpiration());
//    }
//
//    @Test
//    public void testExtractUsername() {
//        String username = "testuser";
//        String token = jwtUtil.generateToken(username);
//
//        String extractedUsername = jwtUtil.extractUsername(token);
//
//        assertEquals(username, extractedUsername);
//    }
//
//    @Test
//    public void testValidateToken() {
//        String username = "testuser";
//        String token = jwtUtil.generateToken(username);
//
//        assertTrue(jwtUtil.validateToken(token, username));
//        assertFalse(jwtUtil.validateToken(token, "wronguser"));
//    }
//
//    @Test
//    public void testIsTokenExpired() {
//        String token = Jwts.builder()
//                .setSubject("testuser")
//                .setIssuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1 hour ago
//                .setExpiration(new Date(System.currentTimeMillis() - 1800000)) // 30 minutes ago
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//
//        assertTrue(jwtUtil.isTokenExpired(token));
//
//        token = jwtUtil.generateToken("testuser");
//        assertFalse(jwtUtil.isTokenExpired(token));
//    }
//}
//
