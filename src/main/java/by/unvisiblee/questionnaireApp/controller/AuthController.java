package by.unvisiblee.questionnaireApp.controller;


import by.unvisiblee.questionnaireApp.dto.AuthResponseDto;
import by.unvisiblee.questionnaireApp.dto.LoginRequestDto;
import by.unvisiblee.questionnaireApp.dto.RegisterRequestDto;
import by.unvisiblee.questionnaireApp.dto.UserDto;
import by.unvisiblee.questionnaireApp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
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
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
    }

    @GetMapping("/user/by-username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getUserByUsername(username));
    }

    @PutMapping("/user/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.updateUser(userDto));
    }
}
