package com.css.gachimeokja.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String socialId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 11, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 255)
    private String authImgUrl;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Column(nullable = false)
    private Integer currentPoint;

    @Builder
    public User(String socialId, String fullName, String email, String phoneNumber,
                LocalDate birthDate, String authImgUrl, String nickname) {
        this.socialId = socialId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.authImgUrl = authImgUrl;
        this.nickname = nickname;
        this.currentPoint = 0; // 초기값 0
    }

    // 메서드
    // 닉네임 변경
    public void updateNickname(String newNickname) {
        if (newNickname == null || newNickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수입니다.");
        }
        this.nickname = newNickname;
    }

    // 포인트 변경
    public void updatePoint(int point) {
        if (point <= 0) {
            throw new IllegalArgumentException("적립할 포인트는 0보다 커야 합니다.");
        }
        this.currentPoint += point;
    }
}
