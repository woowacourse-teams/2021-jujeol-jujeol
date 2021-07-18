package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.MemberDto;
import com.jujeol.drink.application.dto.ReviewRequest;
import com.jujeol.drink.application.dto.ReviewResponse;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.ReviewRepository;
import com.jujeol.drink.exception.CreateReviewLimitException;
import com.jujeol.drink.exception.NotExistReviewInDrinkException;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.exception.NotFoundReviewException;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.exception.NoSuchMemberException;
import com.jujeol.member.exception.UnauthorizedUserException;
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

    @Transactional
    public void createReview(Long loginMemberId, Long drinkId, ReviewRequest reviewRequest) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(NotFoundDrinkException::new);

        validateCreateReviewLimit(loginMemberId, drinkId);

        Review review = Review.from(reviewRequest.getContent(), drink, member);
        Review saveReview = reviewRepository.save(review);
        drink.addReview(saveReview);
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

    public Page<ReviewResponse> showReviews(Long drinkId, Pageable pageable) {
        return reviewRepository.findAllByDrinkId(drinkId, pageable)
                .map(review -> ReviewResponse.from(
                        review.getId(),
                        MemberDto.from(review.getMemberId()),
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getModifiedAt()
                        )
                );
    }

    @Transactional
    public void updateReview(Long loginMemberId, Long drinksId, Long reviewId,
            ReviewRequest reviewRequest) {
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(drinksId).orElseThrow(NotFoundDrinkException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundReviewException::new);

        validateReviewWithDrink(drink, review);
        validateReviewWithMember(member, review);

        review.editContent(reviewRequest.getContent());
    }

    @Transactional
    public void deleteReview(Long loginId, Long drinkId, Long reviewId) {
        Member member = memberRepository.findById(loginId).orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(NotFoundDrinkException::new);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(NotFoundReviewException::new);

        validateReviewWithDrink(drink, review);
        validateReviewWithMember(member, review);

        reviewRepository.delete(review);
        drink.removeReview(review);
    }

    private void validateReviewWithDrink(Drink drink, Review review) {
        if (!review.isReviewOf(drink)) {
            throw new NotExistReviewInDrinkException();
        }
    }

    private void validateReviewWithMember(Member member, Review review) {
        if (!review.isAuthor(member)) {
            throw new UnauthorizedUserException();
        }
    }
}
