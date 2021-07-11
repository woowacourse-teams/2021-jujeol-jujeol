package com.jujeol.commons.exception;

import lombok.Getter;

@Getter
public class JujeolException extends RuntimeException {

    private final ExceptionCodeAndDetails codeAndMessage = ExceptionCodeAndDetails
            .findByClass(this.getClass());

    private String code;
    private String message;

    public JujeolException() {
        this.code = codeAndMessage.getCode();
        this.message = codeAndMessage.getMessage();
    }
}
