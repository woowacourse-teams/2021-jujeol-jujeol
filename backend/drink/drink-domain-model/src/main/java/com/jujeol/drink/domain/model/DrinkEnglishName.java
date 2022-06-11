package com.jujeol.drink.domain.model;

import com.jujeol.drink.domain.exception.InvalidEnglishNameException;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrinkEnglishName {

    private static final Pattern PATTERN =
        Pattern.compile("^[A-Za-z\\d\\t-_]*$");

    private String englishName;

    private DrinkEnglishName(String englishName) {

        if (!isEnglishNamePattern(englishName)) {
            throw new InvalidEnglishNameException();
        }
        this.englishName = englishName;
    }

    public String value() {
        return englishName;
    }

    public static DrinkEnglishName from(String englishName) {
        return new DrinkEnglishName(englishName);
    }

    private boolean isEnglishNamePattern(String englishName) {
        Matcher m = PATTERN.matcher(englishName);
        return m.matches();
    }
}
