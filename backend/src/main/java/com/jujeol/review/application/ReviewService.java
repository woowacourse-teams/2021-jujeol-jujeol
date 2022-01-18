package com.jujeol.review.application;

import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.member.auth.exception.UnauthorizedUserException;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.member.member.exception.NoSuchMemberException;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.repository.PreferenceRepository;
import com.jujeol.review.application.dto.MemberSimpleDto;
import com.jujeol.review.application.dto.ReviewCreateDto;
import com.jujeol.review.application.dto.ReviewWithAuthorDto;
import com.jujeol.review.domain.Review;
import com.jujeol.review.domain.repository.ReviewRepository;
import com.jujeol.review.exception.CreateReviewLimitException;
import com.jujeol.review.exception.CreateReviewNoPreferenceException;
import com.jujeol.review.exception.NotFoundReviewException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;

    @Transactional
    public void createReview(Long loginMemberId, ReviewCreateDto reviewCreateDto) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(reviewCreateDto.getDrinkId())
                .orElseThrow(NotFoundDrinkException::new);

        validateCreateReviewLimit(loginMemberId, reviewCreateDto.getDrinkId());
        validatePreferenceRate(member, drink);

        Review review = Review.create(reviewCreateDto.getContent(), drink, member);
        Review saveReview = reviewRepository.save(review);
        drink.addReview(saveReview);
    }

    private void validatePreferenceRate(Member member, Drink drink) {
        Preference preference = preferenceRepository
                .findByMemberIdAndDrinkId(member.getId(), drink.getId())
                .orElseGet(() -> Preference.create(member, drink, 0.0));

        if (preference.isZeroRate()) {
            throw new CreateReviewNoPreferenceException();
        }
    }


    private void validateCreateReviewLimit(Long loginMemberId, Long drinkId) {
        List<Review> byDrinkIdAndMemberId = reviewRepository
                .findByDrinkIdAndMemberId(drinkId, loginMemberId, Pageable.ofSize(1));

        if (!byDrinkIdAndMemberId.isEmpty()) {
            LocalDate now = LocalDate.now();
            LocalDate createdAt = byDrinkIdAndMemberId.get(0).getCreatedAt().toLocalDate();

            if (createdAt.isEqual(now)) {
                throw new CreateReviewLimitException();
            }
        }
    }

    public Page<ReviewWithAuthorDto> showReviews(Long drinkId, Pageable pageable) {
        return reviewRepository.findAllByDrinkId(drinkId, pageable)
                .map(review -> ReviewWithAuthorDto.create(
                        review,
                        MemberSimpleDto.create(review.getMember())
                        )
                );
    }

    @Transactional
    public void updateReview(Long loginMemberId, Long reviewId,
            String content) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(NoSuchMemberException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundReviewException::new);

        validateReviewWithMember(member, review);

        review.editContent(content);
    }

    @Transactional
    public void deleteReview(Long loginId, Long reviewId) {
        Member member = memberRepository.findById(loginId).orElseThrow(NoSuchMemberException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundReviewException::new);

        validateReviewWithMember(member, review);

        reviewRepository.delete(review);
    }

    private void validateReviewWithMember(Member member, Review review) {
        if (!review.isAuthor(member)) {
            throw new UnauthorizedUserException();
        }
    }
}
