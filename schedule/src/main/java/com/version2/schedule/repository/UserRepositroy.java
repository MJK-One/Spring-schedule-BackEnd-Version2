package com.version2.schedule.repository;

import com.version2.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepositroy extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 이메일 정보 찾기

    Optional<User> findByUserId(Integer userId); // 회원ID 정보 찾기
}
