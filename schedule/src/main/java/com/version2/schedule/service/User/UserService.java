package com.version2.schedule.service.User;

import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.User.UserRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositroy userRepository;

    @Transactional
    public SignupResponseDto signUpUser(String username, String email, String password) {
        if(userRepository.findByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User(username, email, password);

        userRepository.save(user);

        return new SignupResponseDto(user.getUsername(), user.getUsername(), user.getEmail());
    }
}
