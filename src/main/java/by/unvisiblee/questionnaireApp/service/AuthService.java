package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.Repository.UserRepository;
import by.unvisiblee.questionnaireApp.Repository.VerificationTokenRepository;
import by.unvisiblee.questionnaireApp.dto.RegisterRequest;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
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
    }

    private String generateVerificationToken(User user) {
        String verificationTokenString = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(verificationTokenString);

        verificationTokenRepository.save(verificationToken);

        return verificationTokenString;
    }
}
