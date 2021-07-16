package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkDetailResponse;
import com.jujeol.drink.application.dto.DrinkSimpleResponse;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.ReviewRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.exception.NotFoundReviewException;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.exception.NoSuchPreferenceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrinkService {

    @Value("${file-server.url:localhost:8080/docs}")
    private String fileServerUrl;

    private final DrinkRepository drinkRepository;
    private final PreferenceRepository preferenceRepository;
    private final ReviewRepository reviewRepository;

    public List<DrinkSimpleResponse> showDrinks() {
        //todo: 페이지네이션
        return drinkRepository.findAll(Pageable.ofSize(7))
                .get()
                .map(drink -> DrinkSimpleResponse.from(drink, fileServerUrl))
                .collect(Collectors.toList());
    }

    public DrinkDetailResponse showDrinkDetail(Long drinkId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);
        Preference preference = Preference.of(drink, 0.0);

        return DrinkDetailResponse.from(drink, preference, fileServerUrl);
    }

    public DrinkDetailResponse showDrinkDetail(Long drinkId, Long memberId) {
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(memberId, drinkId)
                .orElseThrow(NoSuchPreferenceException::new);

        return DrinkDetailResponse.from(drink, preference, fileServerUrl);
    }

    @Transactional
    public void createReview(Long id, ReviewRequest reviewRequest) {
        Drink drink = drinkRepository.findById(id).orElseThrow(NotFoundDrinkException::new);
        Review saveReview = reviewRepository.save(Review.from(reviewRequest.getContent(), drink));
        drink.addReview(saveReview);
    }

    @Transactional
    public void deleteReview(Long drinkId, Long reviewId) {
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(NotFoundDrinkException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundReviewException::new);

        reviewRepository.delete(review);
        drink.removeReview(review);
    }
}
