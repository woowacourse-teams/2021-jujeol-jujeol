package com.jujeol.drink.domain;

import com.jujeol.drink.exception.InvalidEnglishNameException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DrinkEnglishName {

    private static final Pattern PATTERN =
            Pattern.compile("^[A-Za-z\\d\\t-_]*$");

    @Column
    private String englishName;

    public DrinkEnglishName(String englishName) {

        if (Objects.isNull(englishName) || englishName.isBlank()) {
            this.englishName = null;
            return;
        }

        if (!isEnglishNamePattern(englishName)) {
            throw new InvalidEnglishNameException();
        }
        this.englishName = englishName;
    }

    private boolean isEnglishNamePattern(String englishName) {
        Matcher m = PATTERN.matcher(englishName);
        return m.matches();
    }
}
