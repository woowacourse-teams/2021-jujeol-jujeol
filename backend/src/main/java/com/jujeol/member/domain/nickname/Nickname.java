package com.jujeol.member.domain.nickname;

import com.jujeol.member.exception.InvalidUserNicknameLengthException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

    private static int NAME_LENGTH_LIMIT = 10;

    @Column
    private String nickname;

    private Nickname(String nickname) {
        if (nickname.isBlank() || nickname.length() > NAME_LENGTH_LIMIT) {
            throw new InvalidUserNicknameLengthException();
        }
        this.nickname = nickname;
    }

    public static Nickname create(String nickname) {
        return new Nickname(nickname);
    }
}
