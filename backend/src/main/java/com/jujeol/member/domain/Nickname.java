package com.jujeol.member.domain;

import com.jujeol.member.exception.InvalidUserNicknameLengthException;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    private static int NAME_LENGTH_LIMIT = 10;

    private String nickname;

    public Nickname(String nickname) {
        if (nickname.isBlank() || nickname.length() > NAME_LENGTH_LIMIT) {
            throw new InvalidUserNicknameLengthException();
        }
        this.nickname = nickname;
    }

}
