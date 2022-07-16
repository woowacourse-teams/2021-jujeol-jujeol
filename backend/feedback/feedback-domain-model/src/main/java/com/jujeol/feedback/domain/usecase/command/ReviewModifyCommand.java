package com.jujeol.feedback.domain.usecase.command;

import com.jujeol.feedback.domain.model.ReviewContent;
import lombok.Getter;

@Getter
public class ReviewModifyCommand {

    private final Long memberId;
    private final Long reviewId;
    private final ReviewContent reviewContent;

    private ReviewModifyCommand(Long memberId, Long reviewId, ReviewContent reviewContent) {
        this.memberId = memberId;
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
    }

    public static ReviewModifyCommand create(Long memberId, Long reviewId, ReviewContent reviewContent) {
        return new ReviewModifyCommand(memberId, reviewId, reviewContent);
    }
}
