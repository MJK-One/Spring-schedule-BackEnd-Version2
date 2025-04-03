package com.version2.schedule.controller;

import com.version2.schedule.dto.User.DeleteUserRequestDto;
import com.version2.schedule.dto.User.Login.LoginRequestDto;
import com.version2.schedule.dto.User.Login.LoginResponeDto;
import com.version2.schedule.dto.User.SignUp.SignupRequestDto;
import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findByUserId(@PathVariable Integer userId) {
        UserResponseDTO userResponseDTO = userService.findByUserId(userId);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/password/{userId}")
    public ResponseEntity<UpdatepasswordResponseDto> updatePassword(@PathVariable Integer userId, @RequestBody UpdatePasswordRequestDto requestDto) {
        UpdatepasswordResponseDto updatepasswordResponseDto = userService.updatePassword(userId, requestDto);
        return new ResponseEntity<>(updatepasswordResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponeDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponeDto loginResponseDto = userService.login(requestDto);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId, @RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(userId, requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
