package com.jujeol.feedback.presenter;

import com.jujeol.commons.exception.NotAuthorizedException;
import com.jujeol.feedback.controller.request.ReviewCreateRequest;
import com.jujeol.feedback.controller.response.MemberReviewResponse;
import com.jujeol.feedback.controller.response.ReviewResponse;
import com.jujeol.feedback.service.ReviewService;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ReviewPresenter {

    private final ReviewService reviewService;

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public Page<MemberReviewResponse> getMyReviews(LoginMember loginMember, Pageable pageable) {
        if(loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
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
            throw new NotAuthorizedException();
        }

        reviewService.createReview(loginMember.getId(), reviewCreateRequest.getDrinkId(), reviewCreateRequest.getContent(), now);
    }

    public void updateReview(LoginMember loginMember, Long reviewId, String content) {
        if(loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
        }

        reviewService.updateReview(loginMember.getId(), reviewId, content);
    }
}
