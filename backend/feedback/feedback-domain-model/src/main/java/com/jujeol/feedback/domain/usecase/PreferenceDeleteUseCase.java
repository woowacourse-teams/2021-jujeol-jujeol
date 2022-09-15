package com.jujeol.feedback.domain.usecase;

import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.usecase.command.PreferenceDeleteCommand;

import java.util.Optional;

public interface PreferenceDeleteUseCase {

    void delete(PreferenceDeleteCommand command) throws PreferenceNotExistException;

    interface PreferencePort {
        void delete(Long preferenceId);
        Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId);
    }
}
