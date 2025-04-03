package com.version2.schedule.entity;

import lombok.Getter;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "schedule", schema = "schedule_management")
public class Schedule {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}