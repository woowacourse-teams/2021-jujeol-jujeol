package com.jujeol.feedback.service;

import com.jujeol.drink.domain.reader.DrinkReader;
import com.jujeol.feedback.domain.exception.InvalidReviewRegisterException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.model.ReviewContent;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.feedback.domain.reader.ReviewReader;
import com.jujeol.feedback.domain.usecase.ReviewRegisterUseCase;
import com.jujeol.feedback.domain.usecase.command.ReviewRegisterCommand;
import com.jujeol.feedback.rds.repository.ReviewPageRepository;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.reader.MemberReader;
import com.jujeol.model.ReviewWithDrink;
import com.jujeol.model.ReviewWithMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewPageRepository reviewPageRepository;

    private final DrinkReader drinkReader;
    private final MemberReader memberReader;
    private final ReviewReader reviewReader;
    private final PreferenceReader preferenceReader;

    private final ReviewRegisterUseCase reviewRegisterUseCase;

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

    @Transactional(readOnly = true)
    public Page<ReviewWithMember> findReviewWithMember(Long drinkId, Pageable pageable) {
        Page<Review> reviews = reviewPageRepository.findByDrinkId(drinkId, pageable);
        List<Long> memberIds = reviews.stream().map(Review::getMemberId).distinct().collect(Collectors.toList());

        Map<Long, Member> memberById = new HashMap<>();
        memberReader.findAllByIdsIn(memberIds).forEach(member -> memberById.put(member.getId(), member));

        return reviews.map(review -> ReviewWithMember.create(review, memberById.get(review.getMemberId())));
    }

    @Transactional
    public void createReview(Long memberId, Long drinkId, String content, LocalDateTime now) {
        validateCreateReview(memberId, drinkId, now);

        reviewRegisterUseCase.register(ReviewRegisterCommand.create(memberId, drinkId, ReviewContent.create(content)));
    }

    private void validateCreateReview(Long memberId, Long drinkId, LocalDateTime now) {
        // 날짜 체크
        Optional<Review> previousReview = reviewReader.findByDrinkIdAndMemberId(drinkId, memberId)
            .stream()
            .findFirst();
        if (previousReview.isPresent()) {
            LocalDate createdAt = previousReview.get().getCreatedAt().toLocalDate();
            if (createdAt.isEqual(now.toLocalDate())) {
                throw new InvalidReviewRegisterException();
            }
        }

        // Preference 를 남겼는 지 확인
        Preference preference = preferenceReader.findByDrinkIdAndMemberId(drinkId, memberId).orElseThrow(InvalidReviewRegisterException::new);
        if (preference.getRate() == 0) {
            throw new InvalidReviewRegisterException();
        }
    }
}
