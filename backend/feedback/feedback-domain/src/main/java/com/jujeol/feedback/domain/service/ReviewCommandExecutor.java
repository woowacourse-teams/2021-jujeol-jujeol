package com.jujeol.feedback.domain.service;

import com.jujeol.feedback.domain.exception.NotAuthorizedActionException;
import com.jujeol.feedback.domain.exception.ReviewNotExistException;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.usecase.ReviewModifyUseCase;
import com.jujeol.feedback.domain.usecase.ReviewRegisterUseCase;
import com.jujeol.feedback.domain.usecase.command.ReviewModifyCommand;
import com.jujeol.feedback.domain.usecase.command.ReviewRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReviewCommandExecutor implements
    ReviewRegisterUseCase,
    ReviewModifyUseCase {

    private final ReviewRegisterUseCase.ReviewPort registerReviewPort;
    private final ReviewModifyUseCase.ReviewPort modifyReviewPort;

    @Override
    @Transactional
    public void register(ReviewRegisterCommand command) {
        registerReviewPort.insert(command.getMemberId(), command.getDrinkId(), command.getContent().value());
    }

    @Override
    @Transactional
    public void modify(ReviewModifyCommand command) throws ReviewNotExistException, NotAuthorizedActionException {
        Review review = modifyReviewPort.findById(command.getReviewId()).orElseThrow(ReviewNotExistException::new);
        if (!review.getMemberId().equals(command.getMemberId())) {
            throw new NotAuthorizedActionException();
        }
        modifyReviewPort.update(command.getReviewId(), command.getReviewContent().value());
    }
}
