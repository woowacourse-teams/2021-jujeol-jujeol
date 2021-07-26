package com.jujeol.member.domain.nickname;

import com.jujeol.member.exception.InvalidUserNicknameCharacterException;
import com.jujeol.member.exception.InvalidUserNicknameLengthException;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class Nickname {
    private static final Pattern KOREAN_ENGLISH_DIGIT_UNDERBAR_HYPHEN_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9_-]+$");
    private static final int NAME_LENGTH_LIMIT = 10;

    @Column
    private String nickname;

    private Nickname(String nickname) {
        if (nickname.isBlank() || nickname.length() > NAME_LENGTH_LIMIT) {
            throw new InvalidUserNicknameLengthException();
        }

        if(!KOREAN_ENGLISH_DIGIT_UNDERBAR_HYPHEN_PATTERN.matcher(nickname).matches()){
            throw new InvalidUserNicknameCharacterException();
        }
        this.nickname = nickname;
    }

    public static Nickname create(String nickname) {
        return new Nickname(nickname);
    }
}
