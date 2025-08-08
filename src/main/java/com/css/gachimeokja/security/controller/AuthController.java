package com.css.gachimeokja.security.controller;

import com.css.gachimeokja.security.dto.TokenResponseDto;
import com.css.gachimeokja.security.jwt.JwtTokenProvider;
import com.css.gachimeokja.security.service.KakaoService;
import com.css.gachimeokja.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 프론트엔트에서 콜백 후 code와 함께 POST 요청
    @PostMapping("/kakao/callback")
    public ResponseEntity<TokenResponseDto> kakaoCallback(@RequestBody Map<String, String> requestBody) {
        String code = requestBody.get("code");

        String kakaoAccessToken = kakaoService.getKakaoAccessToken(code);
        Map<String, Object> userInfo = kakaoService.getUserInfo(kakaoAccessToken);
        String socialId = String.valueOf(userInfo.get("id"));

        boolean isUserExists = userRepository.findBySocialId(socialId).isPresent();

        if (isUserExists) {
            String accessToken = jwtTokenProvider.createAccessToken(socialId);
            String refreshToken = jwtTokenProvider.createRefreshToken(socialId);

            // 디버깅용 콘솔 출력
            System.out.println("기존 사용자 로그인 - Access Token 발행: " + accessToken);
            System.out.println("기존 사용자 로그인 - Refresh Token 발행: " + refreshToken);

            return ResponseEntity.ok(new TokenResponseDto(accessToken, refreshToken));
        } else {
            String tempToken = jwtTokenProvider.createTempToken(socialId);

            // 디버깅용 콘솔 출력
            System.out.println("신규 사용자 가입 - 임시 Token 발행: " + tempToken);

            return ResponseEntity.ok(new TokenResponseDto(tempToken, null));
        }
    }
}