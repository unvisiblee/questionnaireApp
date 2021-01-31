package by.unvisiblee.questionnaireApp.util;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

public class GenericResponse {
    private String message;
    private String error;

    public GenericResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public GenericResponse(String message) {
        this.message = message;
    }

    /**
     * Constructor to create a JSON-array of error messages (field(cause) - message couples)
     * [{"message", "can not be empty"} , ...]
     * @param errors - List of errors received from exception
     * @param error - Main error message
     */
    public GenericResponse(List<ObjectError> errors, String error) {
        this.error = error;
        String temp = errors.stream().map(e -> {
            if (e instanceof FieldError) {
                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            } else {
                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
            }
        }).collect(Collectors.joining(","));
        this.message = "[" + temp + "]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
