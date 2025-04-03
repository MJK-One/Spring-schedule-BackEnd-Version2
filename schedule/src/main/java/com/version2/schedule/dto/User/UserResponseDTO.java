package com.version2.schedule.dto.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponseDTO {
    private final Integer id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
