package com.jujeol.drink.domain.reader;

import com.jujeol.drink.domain.model.Category;

import java.util.List;

public interface CategoryReader {

    List<Category> findAll();
}
