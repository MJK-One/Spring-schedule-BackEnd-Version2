package com.version2.schedule.dto.Schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteScheduleRequestDto {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private Integer id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
