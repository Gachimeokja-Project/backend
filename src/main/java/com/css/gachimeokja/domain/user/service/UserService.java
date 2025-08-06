package com.css.gachimeokja.domain.user.service;

import com.css.gachimeokja.domain.user.dto.request.UserSignUpRequest;
import com.css.gachimeokja.domain.user.entity.User;
import com.css.gachimeokja.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 사용자 정보 저장
    @Transactional
    public Long signUp(String socialId, UserSignUpRequest request) {
        // socialId 중복 검사
        if (userRepository.findBySocialId(socialId).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }

        // 닉네임 중복 검사
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // User 엔티티 생성
        User newUser = User.builder()
                .socialId(socialId)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthdate())
                .authImgUrl(request.getAuthImgUrl())
                .nickname(request.getNickname())
                .build();

        // DB 저장
        User savedUser = userRepository.save(newUser);

        return savedUser.getId();
    }
}
