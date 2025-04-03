package com.version2.schedule.dto.Schedule;

import com.version2.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScheduleResponeDto {
    private List<Schedule> schedules;
}
