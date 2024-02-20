package com.billingreports.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> userNameAlreadyExistException(UsernameOrEmailAlreadyExistsException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameOrPasswordIncorrectException.class)
    public ResponseEntity<ErrorDetails> usernameOrPasswordIncorrectException(UsernameOrPasswordIncorrectException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidDateRangeException.class)
    public ResponseEntity<ErrorDetails> validDateRangeException(ValidDateRangeException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorDetails> invalidUserException(InvalidUserException exception, WebRequest webRequest) {
        LocalDateTime localDate = LocalDateTime.now();
        ErrorDetails errorDetails = new ErrorDetails(localDate, exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        errorList.forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
