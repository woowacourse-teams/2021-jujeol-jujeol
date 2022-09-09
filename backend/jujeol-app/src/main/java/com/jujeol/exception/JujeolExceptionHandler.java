package com.jujeol.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.jujeol.exception.ExceptionCodeAndDetails.NOT_FOUND_API;

@Slf4j
@RestControllerAdvice
public class JujeolExceptionHandler {

    @ExceptionHandler(JujeolBadRequestException.class)
    public ResponseEntity<JujeolExceptionResponse> badRequestException(JujeolBadRequestException e) {
        // TODO : 로그
        return ResponseEntity.badRequest()
                             .body(new JujeolExceptionResponse(e.getExceptionCodeAndDetails().getCode(), e.getExceptionCodeAndDetails().getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            BindException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<JujeolExceptionResponse> badRequest(Exception e) {
        // TODO : 로그
        return ResponseEntity.badRequest()
                             .body(new JujeolExceptionResponse(ExceptionCodeAndDetails.BAD_REQUEST.getCode(), ExceptionCodeAndDetails.BAD_REQUEST.getMessage()));
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<JujeolExceptionResponse> handleError404() {
        String code = NOT_FOUND_API.getCode();
        String message = NOT_FOUND_API.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JujeolExceptionResponse(code, message));
    }

    @ExceptionHandler(JujeolServerException.class)
    public ResponseEntity<Void> serverException() {
        // TODO : 로그
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
