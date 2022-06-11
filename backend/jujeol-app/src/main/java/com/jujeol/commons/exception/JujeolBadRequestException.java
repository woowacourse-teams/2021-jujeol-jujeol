package com.jujeol.commons.exception;

import com.jujeol.commons.BadStatus;

// TODO : ControllerAdvice 설정 필요
public class JujeolBadRequestException extends RuntimeException {

    public JujeolBadRequestException(BadStatus badStatus) {
        super(badStatus.getMessage());
    }
}
