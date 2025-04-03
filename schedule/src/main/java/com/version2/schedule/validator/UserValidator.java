package com.version2.schedule.validator;

import com.version2.schedule.entity.User;
import com.version2.schedule.repository.UserRepositroy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepositroy userRepository;

    public User validateUserExists(Integer userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}

