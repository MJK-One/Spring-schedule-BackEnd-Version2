package com.version2.schedule.dto.User;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private String email;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
