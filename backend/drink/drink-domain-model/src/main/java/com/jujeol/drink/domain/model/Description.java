package com.jujeol.drink.domain.model;

import com.jujeol.drink.domain.exception.InvalidDescriptionException;

import java.util.Objects;

public class Description {

    private static final int LIMIT_LENGTH = 1000;

    private String description;

    private Description(String description) {
        if (Objects.isNull(description) || description.isBlank()
            || description.length() > LIMIT_LENGTH) {
            throw new InvalidDescriptionException();
        }
        this.description = description;
    }

    public static Description from(String description) {
        return new Description(description);
    }

    public String value() {
        return description;
    }
}
