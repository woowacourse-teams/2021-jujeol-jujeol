package com.jujeol.member.domain.model;

import com.jujeol.member.domain.exception.InvalidUserBiographyLengthException;

public class Biography {

    private static final int CONTENT_LENGTH_LIMIT = 35;

    private final String biography;

    private Biography(String biography) {
        if (biography.length() > CONTENT_LENGTH_LIMIT) {
            throw new InvalidUserBiographyLengthException();
        }
        this.biography = biography;
    }

    public static Biography create(String biography) {
        return new Biography(biography);
    }

    public String value() {
        return biography;
    }
}
