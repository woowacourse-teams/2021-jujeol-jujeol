package com.jujeol.drink.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchWords {

    private final List<String> searchWords;

    public static SearchWords create(String search) {
        if (search == null) {
            return new SearchWords(new ArrayList<>());
        }
        return new SearchWords(List.of(search.split("\\s")));
    }

    public boolean hasSearchWords() {
        return !searchWords.isEmpty();
    }
}
