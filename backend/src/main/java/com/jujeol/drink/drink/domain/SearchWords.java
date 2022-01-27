package com.jujeol.drink.drink.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchWords {

    private final List<String> searchWords;

    public static SearchWords create(String searchWord) {
        if (searchWord == null) {
            return new SearchWords(new ArrayList<>());
        }
        return new SearchWords(List.of(searchWord.split("\\s")));
    }

    public boolean hasSearchWords() {
        return !searchWords.isEmpty();
    }
}
