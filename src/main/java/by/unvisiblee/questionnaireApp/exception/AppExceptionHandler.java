package by.unvisiblee.questionnaireApp.exception;

import by.unvisiblee.questionnaireApp.util.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {


    public AppExceptionHandler() {
        super();
    }

    //400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders httpHeaders,
                                                                  HttpStatus status, WebRequest request) {
        logger.error("400 Status Code", ex);
        BindingResult bindingResult = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(bindingResult.getAllErrors() , "Invalid" + ex.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
        logger.error("400 Status Code");
        GenericResponse responseBody = new GenericResponse("User with " + ex.getField() + " " + ex.getValue() + " Already exists", "UserAlreadyExists");
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
