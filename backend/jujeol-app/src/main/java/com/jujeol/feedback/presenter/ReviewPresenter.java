package com.jujeol.feedback.presenter;

import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.feedback.controller.request.ReviewCreateRequest;
import com.jujeol.feedback.controller.response.MemberReviewResponse;
import com.jujeol.feedback.controller.response.ReviewResponse;
import com.jujeol.feedback.domain.exception.InvalidReviewRegisterException;
import com.jujeol.feedback.domain.exception.NotAuthorizedActionException;
import com.jujeol.feedback.domain.exception.ReviewNotExistException;
import com.jujeol.feedback.service.ReviewService;
import com.jujeol.member.resolver.LoginMember;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewPresenter {

    private final ReviewService reviewService;

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public Page<MemberReviewResponse> getMyReviews(LoginMember loginMember, Pageable pageable) {
        if(loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        return reviewService.findMyReviews(loginMember.getId(), pageable)
            .map(reviewWithDrink ->
                new MemberReviewResponse(
                    reviewWithDrink.getReview().getId(),
                    reviewWithDrink.getReview().getContent().value(),
                    reviewWithDrink.getReview().getCreatedAt(),
                    reviewWithDrink.getReview().getModifiedAt(),
                    new MemberReviewResponse.DrinkResponse(
                        reviewWithDrink.getDrink().getDrinkId(),
                        reviewWithDrink.getDrink().getName().value(),
                        reviewWithDrink.getDrink().getImageFilePath().getMediumImageFilePath()
                        )
                )
            );
    }

    // TODO : modifiedAt, createdAt 설정 필요
    public Page<ReviewResponse> reviewList(Long drinkId, Pageable pageable) {
        return reviewService.findReviewWithMember(drinkId, pageable)
            .map(reviewWithMember ->
                ReviewResponse.builder()
                    .id(reviewWithMember.getReview().getId())
                    .content(reviewWithMember.getReview().getContent().value())
                    .author(ReviewResponse.MemberSimpleResponse.builder()
                        .id(reviewWithMember.getMember().getId())
                        .name(reviewWithMember.getMember().getNickname().value())
                        .build())
                    .build()
            );
    }

    public void createReview(LoginMember loginMember, ReviewCreateRequest reviewCreateRequest, LocalDateTime now) {
        if(loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        try {
            reviewService.createReview(loginMember.getId(), reviewCreateRequest.getDrinkId(), reviewCreateRequest.getContent(), now);
        } catch (InvalidReviewRegisterException e) {
            switch (e.getReason()) {
                case ONCE_PER_DAY:
                    throw new JujeolBadRequestException(ExceptionCodeAndDetails.CREATE_REVIEW_LIMIT);
                case NO_PREFERENCE:
                    throw new JujeolBadRequestException(ExceptionCodeAndDetails.CREATE_REVIEW_NO_PREFERENCE);
                default:
                    throw new JujeolBadRequestException(ExceptionCodeAndDetails.INVALID_REVIEW_REGISTER);
            }
        }
    }

    public void updateReview(LoginMember loginMember, Long reviewId, String content) {
        if(loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        try {
            reviewService.updateReview(loginMember.getId(), reviewId, content);
        } catch (ReviewNotExistException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.NOT_FOUND_REVIEW);
        } catch (NotAuthorizedActionException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }
    }

    public void deleteReview(LoginMember loginMember, Long reviewId) {
        if(loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        try {
            reviewService.deleteReview(loginMember.getId(), reviewId);
        } catch (ReviewNotExistException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.NOT_FOUND_REVIEW);
        } catch (NotAuthorizedActionException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }
    }
}
