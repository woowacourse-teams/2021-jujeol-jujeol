package com.jujeol.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchWords {

    private final List<String> searchWords;

    private SearchWords(List<String> searchWords) {
        this.searchWords = searchWords;
    }

    public static SearchWords create(String searchWord) {
        if (searchWord == null) {
            return new SearchWords(new ArrayList<>());
        }
        return new SearchWords(List.of(searchWord.split("\\s")));
    }
}
