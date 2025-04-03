package com.version2.schedule.repository.User;

import com.version2.schedule.entity.Schedule;
import com.version2.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserId(User userId);
}
