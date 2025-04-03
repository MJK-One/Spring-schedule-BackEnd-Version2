package com.version2.schedule.controller;

import com.version2.schedule.dto.Schedule.CreateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.entity.Schedule;
import com.version2.schedule.service.User.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/{userId}")
    public ResponseEntity<Schedule> createSchedule(
            @PathVariable Integer userId,
            @Valid @RequestBody CreateScheduleRequestDto requestDto) {
        scheduleService.createSchedule(userId, requestDto.getTitle(), requestDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScheduleResponeDto> findSchedule(@PathVariable Integer userId) {
        ScheduleResponeDto scheduleResponeDto = scheduleService.findByUserId(userId);

        return new ResponseEntity<>(scheduleResponeDto, HttpStatus.OK);
    }
}
