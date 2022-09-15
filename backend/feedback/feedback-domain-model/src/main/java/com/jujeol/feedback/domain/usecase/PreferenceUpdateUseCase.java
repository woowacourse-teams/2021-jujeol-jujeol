package com.jujeol.feedback.domain.usecase;


import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.usecase.command.PreferenceUpdateCommand;

import java.util.Optional;

public interface PreferenceUpdateUseCase {

    void update(PreferenceUpdateCommand command) throws PreferenceNotExistException;

    interface PreferencePort {
        Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId);
        void update(Long id, double rate);
    }
}
