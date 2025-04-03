package com.version2.schedule.repository.User;

import com.version2.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepositroy extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Integer userId);
}
