package com.version2.schedule.controller;

import com.version2.schedule.dto.Schedule.CreateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.DeleteScheduleRequestDto;
import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleResponeDto;
import com.version2.schedule.dto.User.Login.LoginResponeDto;
import com.version2.schedule.entity.Schedule;
import com.version2.schedule.service.ScheduleService;
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
        scheduleService.createSchedule(
                userId,
                requestDto.getTitle(),
                requestDto.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ScheduleResponeDto> findSchedule(@PathVariable Integer userId) {
        ScheduleResponeDto scheduleResponeDto = scheduleService.findByUserId(userId);
        return new ResponseEntity<>(scheduleResponeDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdateScheduleResponeDto> updateSchedule(
            @PathVariable Integer userId,
            @Valid @RequestBody UpdateScheduleRequestDto requestDto
    ) {
        UpdateScheduleResponeDto responeDto = scheduleService.UpdateSchedule(userId, requestDto);
        return new ResponseEntity<>(responeDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteSchedule(
            @PathVariable Integer userId,
            @Valid @RequestBody DeleteScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(userId, requestDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
