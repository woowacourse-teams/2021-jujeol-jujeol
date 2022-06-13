package com.jujeol.member.domain.model;

import com.jujeol.member.domain.exception.InvalidUserBiographyLengthException;
import lombok.Getter;

public class Biography {

    private static int CONTENT_LENGTH_LIMIT = 35;

    private String biography;

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
