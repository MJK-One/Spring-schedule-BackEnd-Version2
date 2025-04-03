package com.version2.schedule.dto.User.UpdatePassword;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String oldPassword; // 현재 비밀번호

    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    private String newPassword; // 새로운 비밀번호
}
