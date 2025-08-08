package com.css.gachimeokja.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct; // Spring Boot 3.x용 import
import java.security.Key;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private Key signingKey;

    @PostConstruct
    protected void init() {
        // Base64로 인코딩된 문자열을 Key 객체로 변환
        String encodedKeyString = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.signingKey = Keys.hmacShaKeyFor(encodedKeyString.getBytes());
    }

    /**
     * Access Token 생성
     * @param socialId 카카오 사용자 식별자
     * @return 생성된 Access Token
     */
    public String createAccessToken(String socialId) {
        return createToken(socialId, ACCESS_TOKEN_EXPIRE_TIME);
    }

    /**
     * Refresh Token 생성
     * @param socialId 카카오 사용자 식별자
     * @return 생성된 Refresh Token
     */
    public String createRefreshToken(String socialId) {
        return createToken(socialId, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String createToken(String userId, long expireTime) {
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT 토큰 유효성 검증
     * @param token 검증할 JWT 토큰
     * @return 유효하면 true, 아니면 false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT 토큰 검증 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * 토큰에서 사용자 ID 추출
     * @param token 사용자 ID를 추출할 JWT 토큰
     * @return 추출된 사용자 ID (socialId)
     */
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 회원가입을 위한 임시 토큰
     * @param socialId 카카오로부터 받은 socialId
     * @return 생성된 임시 토큰
     */
    public String createTempToken(String socialId) {
        long TEMP_TOKEN_EXPIRE_TIME = 300000; // 예: 5분 (밀리초)
        Claims claims = Jwts.claims().setSubject(socialId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + TEMP_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}