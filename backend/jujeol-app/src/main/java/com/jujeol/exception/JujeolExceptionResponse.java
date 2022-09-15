package com.jujeol.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JujeolExceptionResponse {

    private String code;
    private String message;
}
