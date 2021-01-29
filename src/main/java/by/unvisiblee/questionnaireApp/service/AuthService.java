package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.AuthResponseDto;
import by.unvisiblee.questionnaireApp.dto.LoginRequestDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.exception.QuestionnaireServiceException;
import by.unvisiblee.questionnaireApp.exception.UserAlreadyExistException;
import by.unvisiblee.questionnaireApp.repository.UserRepository;
import by.unvisiblee.questionnaireApp.repository.VerificationTokenRepository;
import by.unvisiblee.questionnaireApp.dto.RegisterRequestDto;
import by.unvisiblee.questionnaireApp.model.NotificationEmail;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.model.VerificationToken;
import by.unvisiblee.questionnaireApp.security.JwtProvider;
import by.unvisiblee.questionnaireApp.util.MailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final FormService formService;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       VerificationTokenRepository verificationTokenRepository,
                       MailService mailService, AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider, FormService formService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.formService = formService;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(User.class, principal.getUsername()));
    }

    @Transactional
    public void signup(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByUsername(registerRequestDto.getUsername()))
            throw new UserAlreadyExistException(registerRequestDto.getUsername(), "username");
        if (userRepository.existsByEmail(registerRequestDto.getEmail().toLowerCase()))
            throw new UserAlreadyExistException(registerRequestDto.getEmail(), "email");


        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setFirstName(registerRequestDto.getFirstName());
        user.setLastName(registerRequestDto.getLastName());
        user.setPhoneNumber(registerRequestDto.getPhoneNumber());
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail(user.getEmail(), "Please Activate your Account",
                 "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/verification/" + token));
    }

    @Transactional
    String generateVerificationToken(User user) {
        String verificationTokenString = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(verificationTokenString);

        verificationTokenRepository.save(verificationToken);

        return verificationTokenString;
    }

    public void verifyAccountByToken(String token) {
        Optional<VerificationToken> verificationToken =  verificationTokenRepository.findByToken(token);
        if (verificationToken.isEmpty()) {
            throw new QuestionnaireServiceException("Token does not exist!");
        }
        
        activateUser(verificationToken.get());

    }

    @Transactional
    void activateUser(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        formService.create(user); // when user is activated - create blank form.
    }


    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String authToken = jwtProvider.generateToken(auth);
        return new AuthResponseDto(authToken, loginRequestDto.getUsername());
    }
}
