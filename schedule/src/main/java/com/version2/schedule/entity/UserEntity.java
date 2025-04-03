package com.version2.schedule.entity;

import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId; // 유저 ID

    @Column(name = "username", nullable = false, length = 255)
    private String username; // 유저 이름

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email; // 유저 이메일

    @Column(name = "password", nullable = false, length = 255)
    private String password; // 유저 비밀번호

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime createdAt; // 유저 생성 날짜

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 유저 정보 변경 날짜

    // Getters and Setters
}