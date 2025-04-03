package com.version2.schedule.dto.User;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateDto {
    @Size(min = 2, max = 10, message = "유저명은 2자 이상 10자 이하로 입력해주세요.")
    private final String username;

    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private final String password;
}
