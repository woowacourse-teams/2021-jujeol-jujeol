package com.jujeol.feedback.domain.usecase;

import com.jujeol.feedback.domain.exception.DuplicatePreferenceException;
import com.jujeol.feedback.domain.usecase.command.PreferenceRegisterCommand;

public interface PreferenceRegisterUseCase {

    void register(PreferenceRegisterCommand command) throws DuplicatePreferenceException;

    interface PreferencePort {
        void insert(Long memberId, Long drinkId, double rate);
        boolean existsByMemberIdAndDrinkId(Long memberId, Long drinkId);
    }
}
