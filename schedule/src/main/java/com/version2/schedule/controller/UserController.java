package com.version2.schedule.controller;

import com.version2.schedule.dto.User.DeleteUserRequestDto;
import com.version2.schedule.dto.User.Login.LoginRequestDto;
import com.version2.schedule.dto.User.Login.LoginResponeDto;
import com.version2.schedule.dto.User.SignUp.SignupRequestDto;
import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
import com.version2.schedule.dto.User.UserResponseDTO;
import com.version2.schedule.exception.ErrorResponse;
import com.version2.schedule.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            SignupResponseDto signupResponseDto = userService.signUpUser(
                    requestDto.getUsername(),
                    requestDto.getEmail(),
                    requestDto.getPassword()
            );
            return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리 (예: 중복된 이메일)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); //적절한 응답으로 수정
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findByUserId(@PathVariable Integer userId) {
        try {
            UserResponseDTO userResponseDTO = userService.findByUserId(userId);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); //적절한 응답으로 수정
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UpdatepasswordResponseDto> updatePassword(
            @PathVariable Integer userId,
            @RequestBody UpdatePasswordRequestDto requestDto
    ) {
        try {
            UpdatepasswordResponseDto updatepasswordResponseDto = userService.updatePassword(userId, requestDto);
            return new ResponseEntity<>(updatepasswordResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); //적절한 응답으로 수정
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto, HttpSession session) {
        try {
            LoginResponeDto loginResponseDto = userService.login(requestDto);
            // 세션에 사용자 정보 저장
            session.setAttribute("userId", loginResponseDto.getUserId());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            // 401 Unauthorized 응답 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.of(HttpStatus.UNAUTHORIZED, e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer userId,
            @RequestBody DeleteUserRequestDto requestDto
    ) {
        try {
            userService.deleteUser(userId, requestDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


//import com.version2.schedule.dto.User.DeleteUserRequestDto;
//import com.version2.schedule.dto.User.Login.LoginRequestDto;
//import com.version2.schedule.dto.User.Login.LoginResponeDto;
//import com.version2.schedule.dto.User.SignUp.SignupRequestDto;
//import com.version2.schedule.dto.User.SignUp.SignupResponseDto;
//import com.version2.schedule.dto.User.UpdatePassword.UpdatePasswordRequestDto;
//import com.version2.schedule.dto.User.UpdatePassword.UpdatepasswordResponseDto;
//import com.version2.schedule.dto.User.UserResponseDTO;
//import com.version2.schedule.service.UserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    @PostMapping("/signup")
//    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
//        SignupResponseDto signupResponseDto = userService.signUpUser(
//                requestDto.getUsername(),
//                requestDto.getEmail(),
//                requestDto.getPassword()
//        );
//        return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserResponseDTO> findByUserId(@PathVariable Integer userId) {
//        UserResponseDTO userResponseDTO = userService.findByUserId(userId);
//
//        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<UpdatepasswordResponseDto> updatePassword(
//            @PathVariable Integer userId,
//            @RequestBody UpdatePasswordRequestDto requestDto
//    ) {
//        UpdatepasswordResponseDto updatepasswordResponseDto = userService.updatePassword(userId, requestDto);
//        return new ResponseEntity<>(updatepasswordResponseDto, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponeDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
//        LoginResponeDto loginResponseDto = userService.login(requestDto);
//        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(
//            @PathVariable Integer userId,
//            @RequestBody DeleteUserRequestDto requestDto
//    ) {
//        userService.deleteUser(userId, requestDto);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
