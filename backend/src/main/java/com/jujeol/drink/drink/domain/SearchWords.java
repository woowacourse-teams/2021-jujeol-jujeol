package com.jujeol.drink.drink.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchWords {

    private static final String WILD_CARD = "*";
    private static final String OR = " OR ";
    private static final String EMPTY = " ";

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

    public String makeQueryString() {
        if (Objects.isNull(searchWords) || searchWords.isEmpty()) {
            return WILD_CARD;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < searchWords.size(); i++) {
            final String value = searchWords.get(i);

            if (value.contains(EMPTY)) {
                sb.append("\"*").append(value).append("*\"");
            } else {
                sb.append(WILD_CARD).append(value).append(WILD_CARD);
            }

            if (i != searchWords.size() - 1) {
                sb.append(OR);
            }
        }
        return sb.toString();
    }
}
