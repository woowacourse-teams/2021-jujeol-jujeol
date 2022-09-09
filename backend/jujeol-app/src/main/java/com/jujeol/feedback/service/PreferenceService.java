package com.jujeol.feedback.service;

import com.jujeol.feedback.domain.exception.DuplicatePreferenceException;
import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.usecase.PreferenceDeleteUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceRegisterUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceUpdateUseCase;
import com.jujeol.feedback.domain.usecase.command.PreferenceDeleteCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceRegisterCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRegisterUseCase preferenceRegisterUseCase;
    private final PreferenceUpdateUseCase preferenceUpdateUseCase;
    private final PreferenceDeleteUseCase preferenceDeleteUseCase;

    @Transactional
    public void createOrUpdatePreference(Long memberId, Long drinkId, double preferenceRate) {
        try {
            preferenceRegisterUseCase.register(PreferenceRegisterCommand.create(memberId, drinkId, preferenceRate));
        } catch (DuplicatePreferenceException e) {
            // 이미 존재하는 경우 update
            preferenceUpdateUseCase.update(PreferenceUpdateCommand.create(memberId, drinkId, preferenceRate));
        }
    }

    @Transactional
    public void deletePreference(Long memberId, Long drinkId) {
        try {
            preferenceDeleteUseCase.delete(PreferenceDeleteCommand.create(drinkId, memberId));
        } catch (PreferenceNotExistException ignored) {
            // preference 가 없으면 무시
        }
    }
}
