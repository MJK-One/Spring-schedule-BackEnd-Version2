package com.version2.schedule.controller.User;

import com.version2.schedule.dto.User.SignUp.SignupRequestDto;
import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.service.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto = userService.signUpUser(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
    }

}
