package com.css.gachimeokja.domain.user.repository;

import com.css.gachimeokja.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(String socialId);

    // 닉네임 중복 검사를 위한 닉네임 존재 여부
    boolean existsByNickname(String nickname);
}
