package com.version2.schedule.repository.User;

import com.version2.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositroy extends JpaRepository<User, Long> {
    boolean findByEmail(String email);
}
