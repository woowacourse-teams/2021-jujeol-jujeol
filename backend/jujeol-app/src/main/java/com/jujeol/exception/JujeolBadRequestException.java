package com.jujeol.exception;

import lombok.Getter;

// TODO : ControllerAdvice 설정 필요
@Getter
public class JujeolBadRequestException extends RuntimeException {

    private final ExceptionCodeAndDetails exceptionCodeAndDetails;

    public JujeolBadRequestException(ExceptionCodeAndDetails exceptionCodeAndDetails) {
        super(exceptionCodeAndDetails.getMessage());
        this.exceptionCodeAndDetails = exceptionCodeAndDetails;
    }
}
