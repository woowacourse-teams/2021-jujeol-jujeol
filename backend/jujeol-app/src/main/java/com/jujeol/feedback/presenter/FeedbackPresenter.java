package com.jujeol.feedback.presenter;

import com.jujeol.commons.exception.NotAuthorizedException;
import com.jujeol.feedback.controller.request.UpdatePreferenceRequest;
import com.jujeol.feedback.controller.response.MemberReviewResponse;
import com.jujeol.feedback.service.FeedbackService;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeedbackPresenter {

    private final FeedbackService feedbackService;

    //TODO : Pageable 삭제 필요, Page 응답값 삭제 필요 (응용쪽에서 JPA 기술 의존적 삭제 필요)
    public Page<MemberReviewResponse> getMyReviews(LoginMember loginMember, Pageable pageable) {
        // TODO : unauthorized 정의 필요
        if(loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
        }

        return feedbackService.findMyReviews(loginMember.getId(), pageable)
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

    public void createOrUpdatePreference(LoginMember loginMember, Long drinkId, UpdatePreferenceRequest preferenceRequest) {
        if (loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
        }
        feedbackService.createOrUpdatePreference(loginMember.getId(), drinkId, preferenceRequest.getPreferenceRate());
    }

    public void deletePreference(LoginMember loginMember, Long drinkId) {
        if (loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
        }
        feedbackService.deletePreference(loginMember.getId(), drinkId);
    }
}
