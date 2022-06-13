package com.jujeol.member.domain.model;

import com.jujeol.member.domain.exception.InvalidUserNicknameCharacterException;
import com.jujeol.member.domain.exception.InvalidUserNicknameLengthException;
import lombok.Getter;

import java.util.regex.Pattern;

public class Nickname {

    private static final Pattern KOREAN_ENGLISH_DIGIT_UNDERBAR_HYPHEN_PATTERN = Pattern
        .compile("^[가-힣a-zA-Z0-9_-]+$");
    private static final int NAME_LENGTH_LIMIT = 10;

    private String nickname;

    private Nickname(String nickname) {
        if (nickname.isBlank() || nickname.length() > NAME_LENGTH_LIMIT) {
            throw new InvalidUserNicknameLengthException();
        }

        if (!KOREAN_ENGLISH_DIGIT_UNDERBAR_HYPHEN_PATTERN.matcher(nickname).matches()) {
            throw new InvalidUserNicknameCharacterException();
        }
        this.nickname = nickname;
    }

    public static Nickname create(String nickname) {
        return new Nickname(nickname);
    }

    public String value() {
        return nickname;
    }
}
