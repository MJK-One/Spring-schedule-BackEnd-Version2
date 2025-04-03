package com.version2.schedule.service;

import com.version2.schedule.Config.PasswordEncoder;
import com.version2.schedule.dto.Schedule.DeleteScheduleRequestDto;
import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleResponeDto;
import com.version2.schedule.entity.Schedule;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.ScheduleRepository;
import com.version2.schedule.repository.UserRepositroy;
import com.version2.schedule.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepositroy userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Schedule createSchedule(Integer userId, String title, String content) {
        // 회원 ID 유무 확인
        User findUser = userValidator.validateUserExists(userId);

        Schedule schedule = new Schedule(findUser, title, content);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule;
    }

    public ScheduleResponeDto findByUserId(Integer userId) {
        // 회원 ID 유무 확인
        User findUser = userValidator.validateUserExists(userId);

        // 회원 ID 일정 존재 유무 확인
        List<Schedule> findSchedules = scheduleRepository.findByUserId(findUser);
        if (findSchedules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found for this user");
        }

        return new ScheduleResponeDto(findSchedules);
    }

    @Transactional
    public UpdateScheduleResponeDto UpdateSchedule(Integer userId, UpdateScheduleRequestDto requestDto) {
        // 회원 ID 유무 확인
        User updateUser = userValidator.validateUserExists(userId);

        // 스케줄 ID 유무 확인
        Schedule schedule = scheduleRepository.findById(requestDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        // 스케줄 작성자와 수정 요청자가 동일한지 확인
        if (!schedule.getUserId().equals(updateUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this schedule");
        }

        // 스케줄 업데이트
        schedule.changeSchedule(requestDto.getNewTitle(), requestDto.getNewContent());

        // 업데이트된 스케줄 저장
        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return new UpdateScheduleResponeDto(
                updatedSchedule.getId(),
                updatedSchedule.getTitle(),
                updatedSchedule.getContent()
        );
    }

    @Transactional
    public void deleteSchedule(Integer userId, DeleteScheduleRequestDto requestDto) {
        // 회원 유무 ID 확인
        User deleteUser = userValidator.validateUserExists(userId);

        // 비밀번호 매칭 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), deleteUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // 스케줄 ID 유무 확인
        Schedule schedule = scheduleRepository.findById(requestDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        // 스케줄 작성자와 삭제 요청자가 동일한지 확인
        if (!schedule.getUserId().getUserId().equals(deleteUser.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this schedule");
        }

        scheduleRepository.delete(schedule);
    }
}