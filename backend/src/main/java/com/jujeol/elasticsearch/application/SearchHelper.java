package com.jujeol.elasticsearch.application;

import com.jujeol.elasticsearch.infrastructure.DrinkSearchResult;
import org.springframework.data.domain.Pageable;

public interface SearchHelper {

    DrinkSearchResult searchDrinkIds(String keyword, Pageable pageable);
}
