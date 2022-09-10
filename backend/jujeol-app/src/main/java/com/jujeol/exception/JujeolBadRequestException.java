package com.jujeol.exception;

import lombok.Getter;

@Getter
public class JujeolBadRequestException extends RuntimeException {

    private final ExceptionCodeAndDetails exceptionCodeAndDetails;

    public JujeolBadRequestException(ExceptionCodeAndDetails exceptionCodeAndDetails) {
        super(exceptionCodeAndDetails.getMessage());
        this.exceptionCodeAndDetails = exceptionCodeAndDetails;
    }
}
