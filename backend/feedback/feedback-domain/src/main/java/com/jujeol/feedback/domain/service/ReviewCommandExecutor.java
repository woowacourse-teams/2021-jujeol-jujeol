package com.jujeol.feedback.domain.service;

import com.jujeol.feedback.domain.usecase.ReviewRegisterUseCase;
import com.jujeol.feedback.domain.usecase.command.ReviewRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReviewCommandExecutor implements ReviewRegisterUseCase {

    private final ReviewRegisterUseCase.ReviewPort registerReviewPort;

    @Override
    @Transactional
    public void register(ReviewRegisterCommand command) {
        registerReviewPort.insert(command.getMemberId(), command.getDrinkId(), command.getContent().value());
    }
}
