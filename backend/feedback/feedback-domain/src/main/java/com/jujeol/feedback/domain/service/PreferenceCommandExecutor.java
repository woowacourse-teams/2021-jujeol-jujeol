package com.jujeol.feedback.domain.service;

import com.jujeol.feedback.domain.exception.DuplicatePreferenceException;
import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.usecase.PreferenceRegisterUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceUpdateUseCase;
import com.jujeol.feedback.domain.usecase.command.PreferenceRegisterCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PreferenceCommandExecutor implements
    PreferenceRegisterUseCase,
    PreferenceUpdateUseCase {

    private final PreferenceRegisterUseCase.PreferencePort registerPreferencePort;
    private final PreferenceUpdateUseCase.PreferencePort updatePreferencePort;

    @Override
    @Transactional
    public void register(PreferenceRegisterCommand command) {
        if (registerPreferencePort.existsByMemberIdAndDrinkId(command.getMemberId(), command.getDrinkId())) {
            throw new DuplicatePreferenceException();
        }
        registerPreferencePort.insert(command.getMemberId(), command.getDrinkId(), command.getPreferenceRate());
        // TODO : 선호도 등록 이벤트 발행 (리뷰 평균 점수 업데이트 필요 Drink.preferenceAvg)
    }

    @Override
    @Transactional
    public void update(PreferenceUpdateCommand command) {
        Preference preference = updatePreferencePort.findByDrinkIdAndMemberId(command.getDrinkId(), command.getMemberId())
            .orElseThrow(PreferenceNotExistException::new);

        updatePreferencePort.update(preference.getId(), command.getPreferenceRate());
        // TODO : 선호도 업데이트 이벤트 발행 (리뷰 평균 점수 업데이트 필요 Drink.preferenceAvg)
    }
}
