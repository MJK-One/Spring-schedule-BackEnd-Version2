package com.version2.schedule.service.User;

import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.User.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepository;

    @Transactional
    public SignupResponseDto signUpUser(String username, String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already in use");
                });

        User user = new User(username, email, password);
        User savedUser = userRepository.save(user); // 저장된 User 객체를 받아서 사용

        return new SignupResponseDto(savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword()); // 저장된 User 객체의 정보를 사용
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByUserId(Integer userId) {
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponseDTO(findUser.getUserId(), findUser.getUsername(), findUser.getEmail(), findUser.getCreatedAt(), findUser.getUpdatedAt());
    }
}
