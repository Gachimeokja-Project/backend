package com.css.gachimeokja.security;

import com.css.gachimeokja.security.jwt.JwtAuthenticationFilter;
import com.css.gachimeokja.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (JWT 사용 시)
                .httpBasic(httpBasic -> httpBasic.disable()) // httpBasic 비활성화
                .formLogin(formLogin -> formLogin.disable()) // formLogin 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함 (STATELESS)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/login/**", "/api/v1/signUp/").permitAll() // 로그인 관련 경로는 모두 허용
                        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
                );

        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 비밀번호 인코더 설정 (비밀번호를 다룰 필요가 있다면)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
