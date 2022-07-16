package com.jujeol.feedback.service;

import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.feedback.domain.exception.DuplicatePreferenceException;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.usecase.PreferenceDeleteUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceRegisterUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceUpdateUseCase;
import com.jujeol.feedback.domain.usecase.command.PreferenceDeleteCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceRegisterCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceUpdateCommand;
import com.jujeol.feedback.rds.repository.ReviewPageRepository;
import com.jujeol.model.ReviewWithDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class FeedbackService {

    private final ReviewPageRepository reviewPageRepository;
    private final DrinkReader drinkReader;

    private final PreferenceRegisterUseCase preferenceRegisterUseCase;
    private final PreferenceUpdateUseCase preferenceUpdateUseCase;
    private final PreferenceDeleteUseCase preferenceDeleteUseCase;

    @Transactional(readOnly = true)
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

    @Transactional
    public void createOrUpdatePreference(Long memberId, Long drinkId, double preferenceRate) {
        try {
            preferenceRegisterUseCase.register(PreferenceRegisterCommand.create(memberId, drinkId, preferenceRate));
        } catch (DuplicatePreferenceException e) {
            // 이미 존재하는 경우 update
            preferenceUpdateUseCase.update(PreferenceUpdateCommand.create(memberId, drinkId, preferenceRate));
        }
    }

    @Transactional
    public void deletePreference(Long memberId, Long drinkId) {
        preferenceDeleteUseCase.delete(PreferenceDeleteCommand.create(drinkId, memberId));
    }
}
