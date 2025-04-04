package com.version2.schedule.controller;

import com.version2.schedule.dto.Schedule.CreateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.DeleteScheduleRequestDto;
import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleResponeDto;
import com.version2.schedule.exception.ErrorResponse;
import com.version2.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping
    public ResponseEntity<?> createSchedule(
            HttpSession session,
            @Valid @RequestBody CreateScheduleRequestDto requestDto) {
        // 세션에서 사용자 ID 가져오기
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        }

        try {
            scheduleService.createSchedule(
                    userId,
                    requestDto.getTitle(),
                    requestDto.getContent()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> findSchedule(HttpSession session) {
        // 세션에서 사용자 ID 가져오기
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        }

        try {
            ScheduleResponeDto scheduleResponeDto = scheduleService.findByUserId(userId);
            return new ResponseEntity<>(scheduleResponeDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateSchedule(
            HttpSession session,
            @Valid @RequestBody UpdateScheduleRequestDto requestDto
    ) {
        // 세션에서 사용자 ID 가져오기
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        }

        try {
            UpdateScheduleResponeDto responeDto = scheduleService.UpdateSchedule(userId, requestDto);
            return new ResponseEntity<>(responeDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSchedule(
            HttpSession session,
            @Valid @RequestBody DeleteScheduleRequestDto requestDto) {
        // 세션에서 사용자 ID 가져오기
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        }

        try {
            scheduleService.deleteSchedule(userId, requestDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }
}

// 세션 적용 전 코드
//import com.version2.schedule.dto.Schedule.CreateScheduleRequestDto;
//import com.version2.schedule.dto.Schedule.DeleteScheduleRequestDto;
//import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
//import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleRequestDto;
//import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleResponeDto;
//import com.version2.schedule.dto.User.Login.LoginResponeDto;
//import com.version2.schedule.entity.Schedule;
//import com.version2.schedule.service.ScheduleService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/schedules")
//@RequiredArgsConstructor
//public class ScheduleController {
//    private final ScheduleService scheduleService;
//
//    @PostMapping("/{userId}")
//    public ResponseEntity<Schedule> createSchedule(
//            @PathVariable Integer userId,
//            @Valid @RequestBody CreateScheduleRequestDto requestDto) {
//        scheduleService.createSchedule(
//                userId,
//                requestDto.getTitle(),
//                requestDto.getContent()
//        );
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<ScheduleResponeDto> findSchedule(@PathVariable Integer userId) {
//        ScheduleResponeDto scheduleResponeDto = scheduleService.findByUserId(userId);
//        return new ResponseEntity<>(scheduleResponeDto, HttpStatus.OK);
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<UpdateScheduleResponeDto> updateSchedule(
//            @PathVariable Integer userId,
//            @Valid @RequestBody UpdateScheduleRequestDto requestDto
//    ) {
//        UpdateScheduleResponeDto responeDto = scheduleService.UpdateSchedule(userId, requestDto);
//        return new ResponseEntity<>(responeDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> deleteSchedule(
//            @PathVariable Integer userId,
//            @Valid @RequestBody DeleteScheduleRequestDto requestDto) {
//        scheduleService.deleteSchedule(userId, requestDto);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
