package com.version2.schedule.dto.User.SignUp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
    private final String username;

    private final String email;

    private final String password;
}
