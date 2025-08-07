package com.css.gachimeokja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발 환경용)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/meal-group-purchases/**").permitAll() // 식사공동구매 API 허용
                .requestMatchers("/restaurants/**").permitAll() // 식당 API 허용
                .requestMatchers("/users/**").permitAll() // 사용자 API 허용
                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 허용 (개발용)
                .anyRequest().authenticated() // 나머지는 인증 필요
            )
            .headers(headers -> headers.frameOptions().disable()); // H2 콘솔을 위한 프레임 옵션 비활성화

        return http.build();
    }
}
