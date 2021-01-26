package by.unvisiblee.questionnaireApp.validation;

import by.unvisiblee.questionnaireApp.dto.RegisterRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequestDto> {
    @Override
    public boolean isValid(RegisterRequestDto value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getPasswordConfirm());
    }
}
