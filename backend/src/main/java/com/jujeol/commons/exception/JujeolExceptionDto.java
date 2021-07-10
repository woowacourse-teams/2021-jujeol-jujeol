package com.jujeol.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JujeolExceptionDto {

    private String code;
    private String message;
}
