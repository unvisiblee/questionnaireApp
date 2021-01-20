package by.unvisiblee.questionnaireApp.dto;

import by.unvisiblee.questionnaireApp.validation.PasswordMatches;
import by.unvisiblee.questionnaireApp.validation.ValidEmail;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    @ValidEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public RegisterRequest() {
    }

    public RegisterRequest(@NotBlank String username, @NotBlank String email, @NotBlank String password, @NotBlank String passwordConfirm, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
