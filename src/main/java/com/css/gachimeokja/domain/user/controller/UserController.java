package com.css.gachimeokja.domain.user.controller;

import com.css.gachimeokja.domain.user.dto.request.UserSignUpRequest;
import com.css.gachimeokja.domain.user.service.UserService;
import com.css.gachimeokja.security.dto.TokenResponseDto;
import com.css.gachimeokja.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 카카오 OAuth 로그인 후 최초 회원가입
    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signUp(
        @AuthenticationPrincipal String socialId,
        @RequestBody @Valid UserSignUpRequest request) {

        try {
            Long newUserId = userService.signUp(socialId, request);

            // 회원가입 성공 후 최종 JWT 토큰 발급
            String accessToken = jwtTokenProvider.createAccessToken(socialId);
            String refreshToken = jwtTokenProvider.createRefreshToken(socialId);

            TokenResponseDto tokenResponse = new TokenResponseDto(accessToken, refreshToken);
            return ResponseEntity.ok(tokenResponse);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 에러 발생 시 토큰을 반환하지 않음
        }
    }
}