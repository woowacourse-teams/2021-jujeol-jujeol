package com.jujeol.member.domain;

import com.jujeol.member.exception.InvalidUserBiographyLengthException;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Biography {
    private static int CONTENT_LENGTH_LIMIT = 35;

    private String nickname;

    public Biography(String nickname) {
        if (nickname.length() > CONTENT_LENGTH_LIMIT) {
            throw new InvalidUserBiographyLengthException();
        }
        this.nickname = nickname;
    }
}
