package com.jujeol.feedback.service;

import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.rds.repository.ReviewPageRepository;
import com.jujeol.model.ReviewWithDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class FeedbackService {

    private final ReviewPageRepository reviewPageRepository;
    private final DrinkReader drinkReader;

    public Page<ReviewWithDrink> findMyReviews(Long memberId, Pageable pageable) {
        Page<Review> reviews = reviewPageRepository.findByMemberId(memberId, pageable);

        Pageable resultPageable = reviews.getPageable();
        long totalElements = reviews.getTotalElements();
        Map<Long, List<Review>> reviewByDrinkId = reviews.stream().collect(groupingBy(Review::getDrinkId));

        List<ReviewWithDrink> contents = drinkReader.findAllByDrinkIdIn(reviewByDrinkId.keySet())
            .stream()
            .flatMap(drink -> reviewByDrinkId.get(drink.getDrinkId())
                .stream()
                .map(review -> ReviewWithDrink.create(drink, review)))
            .collect(Collectors.toList());

        return new PageImpl<>(contents, resultPageable, totalElements);
    }
}
