package com.jujeol.member.domain;

import com.jujeol.member.exception.InvalidUserBiographyLengthException;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
}
