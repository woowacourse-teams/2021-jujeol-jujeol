package com.jujeol.feedback.domain.usecase.command;

import com.jujeol.feedback.domain.model.ReviewContent;
import lombok.Getter;

@Getter
public class ReviewRegisterCommand {

    private final Long memberId;
    private final Long drinkId;
    private final ReviewContent content;

    public static ReviewRegisterCommand create(Long memberId, Long drinkId, ReviewContent content) {
        return new ReviewRegisterCommand(memberId, drinkId, content);
    }

    public ReviewRegisterCommand(Long memberId, Long drinkId, ReviewContent content) {
        this.memberId = memberId;
        this.drinkId = drinkId;
        this.content = content;
    }
}
