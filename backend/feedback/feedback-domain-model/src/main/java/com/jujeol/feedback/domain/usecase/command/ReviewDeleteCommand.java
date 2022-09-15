package com.jujeol.feedback.domain.usecase.command;

import lombok.Getter;

@Getter
public class ReviewDeleteCommand {

    private final Long memberId;
    private final Long reviewId;

    private ReviewDeleteCommand(Long memberId, Long reviewId) {
        this.memberId = memberId;
        this.reviewId = reviewId;
    }

    public static ReviewDeleteCommand create(Long memberId, Long reviewId) {
        return new ReviewDeleteCommand(memberId, reviewId);
    }
}
