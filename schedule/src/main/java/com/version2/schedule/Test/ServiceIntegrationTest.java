import com.version2.schedule.Config.PasswordEncoder;
import com.version2.schedule.dto.Schedule.DeleteScheduleRequestDto;
import com.version2.schedule.dto.Schedule.ScheduleResponeDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleRequestDto;
import com.version2.schedule.dto.Schedule.UpdateSchedule.UpdateScheduleResponeDto;
import com.version2.schedule.dto.User.LoginRequestDto;
import com.version2.schedule.dto.User.LoginResponeDto;
import com.version2.schedule.dto.User.SignupResponseDto;
import com.version2.schedule.entity.Schedule;
import com.version2.schedule.entity.User;
import com.version2.schedule.repository.ScheduleRepository;
import com.version2.schedule.repository.UserRepositroy;
import com.version2.schedule.service.ScheduleService;
import com.version2.schedule.service.UserService;
import com.version2.schedule.validator.UserValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("통합 테스트")
public class ServiceIntegrationTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private UserRepositroy userRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ScheduleService scheduleService;

    @InjectMocks
    private UserService userService;

    private static User testUser;

    @BeforeAll
    static void beforeAll() {
        System.out.println("전체 테스트 시작 전");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("전체 테스트 종료 후");
    }

    @BeforeEach
    void setUp() {
        // 테스트에 사용할 User 객체 생성
        testUser = new User("tester", "test@example.com", "encodedPassword");
        testUser.setUserId(1); // ID 설정
    }

    @AfterEach
    void tearDown() {
        System.out.println("각 테스트 종료 후");
    }

    @Test
    @DisplayName("ScheduleService - 일정 생성 테스트")
    void createScheduleTest() {
        // Given
        when(userValidator.validateUserExists(1)).thenReturn(testUser);
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Schedule createdSchedule = scheduleService.createSchedule(1, "Test Schedule", "Test Content");

        // Then
        assertNotNull(createdSchedule);
        assertEquals("Test Schedule", createdSchedule.getTitle());
        assertEquals("Test Content", createdSchedule.getContent());
    }

    @Test
    @DisplayName("ScheduleService - 일정 수정 테스트")
    void updateScheduleTest() {
        // Given
        Schedule schedule = new Schedule(testUser, "Original Title", "Original Content");
        schedule.setId(1); // ID 설정

        UpdateScheduleRequestDto requestDto = new UpdateScheduleRequestDto();
        requestDto.setId(1);
        requestDto.setNewTitle("Updated Title");
        requestDto.setNewContent("Updated Content");

        when(userValidator.validateUserExists(1)).thenReturn(testUser);
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        UpdateScheduleResponeDto updatedSchedule = scheduleService.UpdateSchedule(1, requestDto);

        // Then
        assertNotNull(updatedSchedule);
        assertEquals("Updated Title", updatedSchedule.getTitle());
        assertEquals("Updated Content", updatedSchedule.getContent());
    }

    @Test
    @DisplayName("UserService - 회원 가입 테스트")
    void signUpUserTest() {
        // Given
        String username = "newuser";
        String email = "newuser@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setPassword(encodedPassword); // 암호화된 비밀번호 설정
            return user;
        });

        // When
        SignupResponseDto signupResponse = userService.signUpUser(username, email, password);

        // Then
        assertNotNull(signupResponse);
        assertEquals(username, signupResponse.getUsername());
        assertEquals(email, signupResponse.getEmail());
        assertEquals(encodedPassword, signupResponse.getPassword()); // 암호화된 비밀번호 확인
        verify(passwordEncoder).encode(password); // passwordEncoder.encode()가 호출되었는지 확인
    }

    @Test
    @DisplayName("UserService - 로그인 테스트")
    void loginTest() {
        // Given
        String email = "test@example.com";
        String password = "password";

        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail(email);
        requestDto.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        // When
        LoginResponeDto loginResponse = userService.login(requestDto);

        // Then
        assertNotNull(loginResponse);
        assertEquals(1, loginResponse.getUserId());
        assertEquals(email, loginResponse.getEmail());
    }

    @Test
    @DisplayName("UserService - 비밀번호 암호화 테스트")
    void passwordEncodingTest() {
        // Given
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // When
        String result = passwordEncoder.encode(password);

        // Then
        assertEquals(encodedPassword, result);
    }
}
