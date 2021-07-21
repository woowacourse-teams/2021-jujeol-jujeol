package com.jujeol.member.domain;

import com.jujeol.member.exception.InvalidNicknameLengthException;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Nickname {

    private static int NAME_LENGTH_LIMIT = 10;

    private String nickname;

    public Nickname(String nickname) {
        if (nickname.isEmpty() || nickname.length() > NAME_LENGTH_LIMIT) {
            throw new InvalidNicknameLengthException();
        }
        this.nickname = nickname;
    }

}
