package com.version2.schedule.dto.Schedule.UpdateSchedule;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateScheduleRequestDto {
    @NotBlank(message = "일정 번호는 필수 입력 값입니다.")
    private Integer id;

    @NotBlank(message = "일정 제목은 필수 입력 값입니다.")
    @Size(max = 101, message = "100자 이하로 입력해주세요.")
    private String newTitle;

    private String newContent;
}
