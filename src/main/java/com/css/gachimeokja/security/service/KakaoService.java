package com.css.gachimeokja.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
public class KakaoService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.token-uri}")
    private String tokenUri;

    @Value("${kakao.user-info-uri}")
    private String userInfoUri;

    /**
     * 인가 코드로 카카오 액세스 토큰을 발급받습니다.
     * @param code 카카오로부터 받은 인가 코드
     * @return 카카오 액세스 토큰
     */
    public String getKakaoAccessToken(String code) {
        WebClient webClient = WebClient.create();

        // HTTP 요청 본문 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        Map<String, Object> response = webClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return Optional.ofNullable(response.get("access_token"))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElseThrow(() -> new RuntimeException("카카오 액세스 토큰을 가져오지 못했습니다."));
    }

    /**
     * 카카오 액세스 토큰으로 사용자 정보를 조회합니다.
     * @param kakaoAccessToken 카카오 액세스 토큰
     * @return 사용자 정보 (ID, 닉네임, 이메일 등)
     */
    public Map<String, Object> getUserInfo(String kakaoAccessToken) {
        WebClient webClient = WebClient.create();

        Map<String, Object> userInfoResponse = webClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoAccessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        return userInfoResponse;
    }
}
