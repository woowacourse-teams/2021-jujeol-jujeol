package com.jujeol.review.domain.repository;

import com.jujeol.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {

    Page<Review> findAllByDrinkId(Long drinkId, Pageable pageable);

    List<Review> findByDrinkIdAndMemberId(Long drinkId, Long memberId, Pageable pageable);

    Page<Review> findReviewsOfMine(Long memberId, Pageable pageable);
}
