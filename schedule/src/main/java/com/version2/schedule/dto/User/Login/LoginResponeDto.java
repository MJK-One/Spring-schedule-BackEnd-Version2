package com.version2.schedule.dto.User.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponeDto {
    private final Integer userId;
    private final String email;
}
