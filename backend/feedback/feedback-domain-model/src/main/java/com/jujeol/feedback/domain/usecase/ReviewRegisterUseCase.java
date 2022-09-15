package com.jujeol.feedback.domain.usecase;

import com.jujeol.feedback.domain.usecase.command.ReviewRegisterCommand;

public interface ReviewRegisterUseCase {

    void register(ReviewRegisterCommand command);

    interface ReviewPort {
        void insert(Long memberId, Long drinkId, String content);
    }
}
