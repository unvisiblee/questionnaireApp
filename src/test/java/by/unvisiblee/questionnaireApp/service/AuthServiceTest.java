package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.QuestionnaireAppApplication;
import by.unvisiblee.questionnaireApp.controller.AuthController;
import by.unvisiblee.questionnaireApp.dto.RegisterRequestDto;
import by.unvisiblee.questionnaireApp.dto.UserDto;
import by.unvisiblee.questionnaireApp.exception.QuestionnaireServiceException;
import by.unvisiblee.questionnaireApp.exception.UserAlreadyExistException;
import by.unvisiblee.questionnaireApp.model.NotificationEmail;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.model.VerificationToken;
import by.unvisiblee.questionnaireApp.repository.UserRepository;
import by.unvisiblee.questionnaireApp.repository.VerificationTokenRepository;
import by.unvisiblee.questionnaireApp.util.MailService;
import liquibase.pro.packaged.U;
import org.checkerframework.checker.nullness.Opt;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = QuestionnaireAppApplication.class)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private VerificationTokenRepository verificationTokenRepository;

    @MockBean
    private FormService formService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(authService).isNotNull();
    }

    @Test
    public void signupSuccess() {
        RegisterRequestDto registerRequestDto = getRegisterRequestDto();

        authService.signup(registerRequestDto);

        Mockito.verify(userRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));

        Mockito.verify(mailService, Mockito.times(1))
                .sendMail(ArgumentMatchers.any(NotificationEmail.class));

        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(ArgumentMatchers.any(CharSequence.class));

        Mockito.verify(verificationTokenRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(VerificationToken.class));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void signupFailExistsByUsername() {
        RegisterRequestDto registerRequestDto = getRegisterRequestDto();

        Mockito.doReturn(true)
                .when(userRepository)
                .existsByUsername(registerRequestDto.getUsername());

        authService.signup(registerRequestDto);

        Mockito.verify(userRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(User.class));

        Mockito.verify(mailService, Mockito.times(0))
                .sendMail(ArgumentMatchers.any(NotificationEmail.class));

        Mockito.verify(passwordEncoder, Mockito.times(0))
                .encode(ArgumentMatchers.any(CharSequence.class));

        Mockito.verify(verificationTokenRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(VerificationToken.class));
    }


    @Test(expected = UserAlreadyExistException.class)
    public void signupFailExistsByEmail() {
        RegisterRequestDto registerRequestDto = getRegisterRequestDto();

        Mockito.doReturn(true)
                .when(userRepository)
                .existsByEmail(registerRequestDto.getEmail());

        authService.signup(registerRequestDto);

        Mockito.verify(userRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(User.class));

        Mockito.verify(mailService, Mockito.times(0))
                .sendMail(ArgumentMatchers.any(NotificationEmail.class));

        Mockito.verify(passwordEncoder, Mockito.times(0))
                .encode(ArgumentMatchers.any(CharSequence.class));

        Mockito.verify(verificationTokenRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(VerificationToken.class));
    }


    private RegisterRequestDto getRegisterRequestDto() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("testUsername");
        registerRequestDto.setEmail("test@mail.ru");
        registerRequestDto.setPassword("test");
        registerRequestDto.setPasswordConfirm("test");
        return registerRequestDto;
    }

    @Test
    public void verifyAccountByToken() {
        String token = "RANDOMVERIFICATIONTOKEN";
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(new User());

        Mockito.doReturn(Optional.of(verificationToken))
                .when(verificationTokenRepository)
                .findByToken(token);

        authService.verifyAccountByToken(token);

        Mockito.verify(verificationTokenRepository, Mockito.times(1))
                .findByToken(token);

        Mockito.verify(userRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));
    }

    @Test(expected = QuestionnaireServiceException.class)
    public void verifyAccountByTokenFailedTokenNotExists() {
        String token = "RANDOMVERIFICATIONTOKEN";

        Mockito.doReturn(Optional.empty())
                .when(verificationTokenRepository)
                .findByToken(token);

        authService.verifyAccountByToken(token);

        Mockito.verify(verificationTokenRepository, Mockito.times(1))
                .findByToken(token);

        Mockito.verify(userRepository, Mockito.times(0))
                .save(ArgumentMatchers.any(User.class));
    }
}
