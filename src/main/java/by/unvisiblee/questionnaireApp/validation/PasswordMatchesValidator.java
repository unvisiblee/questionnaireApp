package by.unvisiblee.questionnaireApp.validation;

import by.unvisiblee.questionnaireApp.dto.RegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {
    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        return value.getPassword().equals(value.getPasswordConfirm());
    }
}
