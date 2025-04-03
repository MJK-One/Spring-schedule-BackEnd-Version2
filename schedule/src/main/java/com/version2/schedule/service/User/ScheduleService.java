package com.version2.schedule.service.User;

import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.entity.Schedule;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.User.ScheduleRepository;
import com.version2.schedule.repository.User.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepositroy userRepository;

    public Schedule createSchedule(Integer userId, String title, String content) {
        // 회원 ID 유무 확인
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Schedule 객체 생성 및 User 정보 설정
        Schedule schedule = new Schedule(findUser, title, content);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule;
    }

    public ScheduleResponeDto findByUserId(Integer userId) {
        // 회원 ID 유무 확인
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 회원 ID 일정 존재 유무 확인
        List<Schedule> findSchedules = scheduleRepository.findByUserId(findUser); // List<Schedule>로 변경

        if (findSchedules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found for this user");
        }

        return new ScheduleResponeDto(findSchedules);
    }
}