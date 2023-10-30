package com.Instagram.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException e, WebRequest req) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorDetails> PostExceptionHandler(PostException e, WebRequest req) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorDetails error = new ErrorDetails(e.getBindingResult().getFieldError().getDefaultMessage(),
                "Validation error", LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> OtherExceptionHandler(Exception e, WebRequest req) {
        ErrorDetails error = new ErrorDetails(e.getMessage(), req.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

}
