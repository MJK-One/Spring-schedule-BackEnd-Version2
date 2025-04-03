package com.version2.schedule.service.User;

import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.User.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepository;

    @Transactional
    public SignupResponseDto signUpUser(String username, String email, String password) {
        // 이메일 중복 방지
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already in use");
                });

        User user = new User(username, email, password);
        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByUserId(Integer userId) {
        // 회원 ID 유무 확인
        User findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new UserResponseDTO(findUser.getUserId(), findUser.getUsername(), findUser.getEmail(), findUser.getCreatedAt(), findUser.getUpdatedAt());
    }

    @Transactional
    public UpdatepasswordResponseDto updatePassword(Integer userId, UpdatePasswordRequestDto requestDto) {
        // 회원 ID 유무 확인
        User updateUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 기존 비밀번호 일치 확인
        if (!updateUser.getPassword().equals(requestDto.getOldPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 새로운 비밀번호 설정
        updateUser.changePassword(requestDto.getNewPassword());
        userRepository.save(updateUser);

        return new UpdatepasswordResponseDto(updateUser.getPassword());
    }
}
