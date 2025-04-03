package com.version2.schedule.service;

import com.version2.schedule.Config.PasswordEncoder;
import com.version2.schedule.validator.UserValidator;
import com.version2.schedule.dto.User.DeleteUserRequestDto;
import com.version2.schedule.dto.User.Login.LoginRequestDto;
import com.version2.schedule.dto.User.Login.LoginResponeDto;
import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입
    private final UserValidator userValidator;

    @Transactional
    public LoginResponeDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        // 비밀번호 매칭 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        return new LoginResponeDto(user.getUserId(), user.getEmail());
    }

    @Transactional
    public SignupResponseDto signUpUser(String username, String email, String password) {
        // 이메일 중복 방지
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email already in use");
                });

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodedPassword); // 암호화된 비밀번호 저장
        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getUsername(), savedUser.getEmail(), savedUser.getPassword()); // 암호화된 비밀번호 반환 (주의!)
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findByUserId(Integer userId) {
        // 회원 ID 유무 확인
        User findUser = userValidator.validateUserExists(userId);

        return new UserResponseDTO(findUser.getUserId(), findUser.getUsername(), findUser.getEmail(), findUser.getCreatedAt(), findUser.getUpdatedAt());
    }

    @Transactional
    public UpdatepasswordResponseDto updatePassword(Integer userId, UpdatePasswordRequestDto requestDto) {
        // 회원 ID 유무 확인
        User updateUser = userValidator.validateUserExists(userId);

        // 기존 비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDto.getOldPassword(), updateUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password does not match.");
        }

        // 새로운 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());

        // 새로운 비밀번호 설정
        updateUser.changePassword(encodedPassword); // 암호화된 비밀번호 저장
        userRepository.save(updateUser);

        return new UpdatepasswordResponseDto(updateUser.getPassword()); // 암호화된 비밀번호 반환 (주의!)
    }

    @Transactional
    public void deleteUser(Integer userId, DeleteUserRequestDto requestDto) {
        // 회원 유무 ID 확인
        User deleteUser = userValidator.validateUserExists(userId);

        // 비밀번호 매칭 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), deleteUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        userRepository.delete(deleteUser);
    }
}

