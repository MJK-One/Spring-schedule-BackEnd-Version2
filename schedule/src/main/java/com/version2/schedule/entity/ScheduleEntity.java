package com.version2.schedule.entity;

import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "Schedule")
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 스케줄 ID

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserEntity userid; // 유저 ID

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 일정 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 일정 내용

    @Column(name = "createdAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable=false)
    private LocalDateTime createdAt; // 일정 생성 날짜

    @Column(name = "updatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; // 일정 수정 날짜

    // Getters and Setters
}

