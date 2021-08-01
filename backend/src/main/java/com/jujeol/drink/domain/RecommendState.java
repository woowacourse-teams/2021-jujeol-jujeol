package com.jujeol.drink.domain;

import java.util.List;

public interface RecommendState {

    List<Drink> recommend(Long memberId, int pageSize);
}
