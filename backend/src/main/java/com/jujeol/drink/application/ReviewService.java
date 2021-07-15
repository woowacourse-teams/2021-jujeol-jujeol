package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.ReviewRepository;
import com.jujeol.drink.exception.NotExistReviewInDrinkException;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.exception.NotFoundReviewException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(Long id, ReviewRequest reviewRequest) {
        Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);

        Review saveReview = reviewRepository.save(Review.from(reviewRequest.getContent(), drink));
        drink.addReview(saveReview);
    }

    public Page<ReviewResponse> showReviews(Long drinkId, Pageable pageable) {
        // TODO : memberDto null 제거
        return reviewRepository.findAllByDrinkId(drinkId, pageable)
                .map(review -> ReviewResponse.from(review.getId(), null, review.getContent()));
    }

    @Transactional
    public void updateReview(Long drinksId, Long reviewId, ReviewRequest reviewRequest) {
        Drink drink = drinkRepository.findById(drinksId).orElseThrow(NotFoundDrinkException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);

        validateReviewWithDrink(drink, review);

        review.editContent(reviewRequest.getContent());
    }

    @Transactional
    public void deleteReview(Long drinkId, Long reviewId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(NotFoundDrinkException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);

        validateReviewWithDrink(drink, review);

        reviewRepository.delete(review);
        drink.removeReview(review);
    }

    private void validateReviewWithDrink(Drink drink, Review review) {
        if (!review.isReviewOf(drink)) {
            throw new NotExistReviewInDrinkException();
        }
    }
}
