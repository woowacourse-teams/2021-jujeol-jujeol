package com.jujeol.commons.exception;

import static com.jujeol.commons.exception.ExceptionCodeAndDetails.NOT_FOUND_API;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(JujeolException.class)
    public ResponseEntity<JujeolExceptionDto> loginExceptionHandler(JujeolException exception) {
        return ResponseEntity.badRequest()
                .body(new JujeolExceptionDto(exception.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<JujeolExceptionDto> handleError404() {
        String code = NOT_FOUND_API.getCode();
        String message = NOT_FOUND_API.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new JujeolExceptionDto(code, message));
    }
}
