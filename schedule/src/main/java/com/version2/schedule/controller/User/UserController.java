package com.version2.schedule.controller.User;

import com.version2.schedule.dto.User.SignUp.SignupRequestDto;
import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.service.User.UserService;
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

    @PutMapping("/{userId}/password")
    public ResponseEntity<UpdatepasswordResponseDto> updatePassword(@PathVariable Integer userId, @RequestBody UpdatePasswordRequestDto requestDto) {
        UpdatepasswordResponseDto updatepasswordResponseDto = userService.updatePassword(userId, requestDto);
        return new ResponseEntity<>(updatepasswordResponseDto, HttpStatus.OK);
    }
}
