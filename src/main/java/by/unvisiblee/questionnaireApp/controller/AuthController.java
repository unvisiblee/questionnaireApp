package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.AuthResponseDto;
import by.unvisiblee.questionnaireApp.dto.LoginRequestDto;
import by.unvisiblee.questionnaireApp.dto.RegisterRequestDto;
import by.unvisiblee.questionnaireApp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid final RegisterRequestDto registerRequestDto) {
        authService.signup(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccountByToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }
}
