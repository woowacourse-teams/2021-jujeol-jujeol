package com.jujeol.feedback.domain.reader;

import com.jujeol.feedback.domain.model.Review;

import java.util.List;

public interface ReviewReader {

    List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId);
}
