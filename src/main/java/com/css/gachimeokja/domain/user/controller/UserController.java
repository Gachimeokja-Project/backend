package com.css.gachimeokja.domain.user.controller;

import com.css.gachimeokja.domain.user.dto.request.UserSignUpRequest;
import com.css.gachimeokja.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 카카오 OAuth 로그인 후 최초 회원가입
    @PostMapping("/api/v1/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid UserSignUpRequest request) {
        // -- 카카오 OAuth 인증 후 얻은 socialId를 가져오는 로직 자리 --
        String socialId = "socialId";

        try {
            Long newUserId = userService.signUp(socialId, request);
            return ResponseEntity.ok("회원가입 성공. 회원 ID: " + newUserId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
