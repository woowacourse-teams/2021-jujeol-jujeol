package com.jujeol.feedback.domain.usecase;

import com.jujeol.feedback.domain.exception.NotAuthorizedActionException;
import com.jujeol.feedback.domain.exception.ReviewNotExistException;
import com.jujeol.feedback.domain.model.Review;
import com.jujeol.feedback.domain.usecase.command.ReviewDeleteCommand;

import java.util.Optional;

public interface ReviewDeleteUseCase {

    void delete(ReviewDeleteCommand command) throws ReviewNotExistException, NotAuthorizedActionException;

    interface ReviewPort {
        Optional<Review> findById(Long id);
        void deleteById(Long id);
    }
}
