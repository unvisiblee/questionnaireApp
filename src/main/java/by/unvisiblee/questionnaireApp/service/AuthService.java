package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.AuthResponse;
import by.unvisiblee.questionnaireApp.dto.LoginRequest;
import by.unvisiblee.questionnaireApp.exception.QuestionnaireServiceException;
import by.unvisiblee.questionnaireApp.exception.UserAlreadyExistException;
import by.unvisiblee.questionnaireApp.repository.UserRepository;
import by.unvisiblee.questionnaireApp.repository.VerificationTokenRepository;
import by.unvisiblee.questionnaireApp.dto.RegisterRequest;
import by.unvisiblee.questionnaireApp.model.NotificationEmail;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.model.VerificationToken;
import by.unvisiblee.questionnaireApp.security.JwtProvider;
import by.unvisiblee.questionnaireApp.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       VerificationTokenRepository verificationTokenRepository,
                       MailService mailService, AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new UserAlreadyExistException(registerRequest.getUsername(), "username");
        if (userRepository.existsByEmail(registerRequest.getEmail().toLowerCase()))
            throw new UserAlreadyExistException(registerRequest.getEmail(), "email");


        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
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
    }


    public AuthResponse login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String authToken = jwtProvider.generateToken(auth);
        return new AuthResponse(authToken, loginRequest.getUsername());
    }
}
