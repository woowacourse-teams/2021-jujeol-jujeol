package com.jujeol.feedback.domain.service;

import com.jujeol.feedback.domain.event.PreferenceEvent;
import com.jujeol.feedback.domain.event.PreferenceEvent.EventType;
import com.jujeol.feedback.domain.exception.DuplicatePreferenceException;
import com.jujeol.feedback.domain.exception.NotAuthorizedActionException;
import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.usecase.PreferenceDeleteUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceRegisterUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceUpdateUseCase;
import com.jujeol.feedback.domain.usecase.command.PreferenceDeleteCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceRegisterCommand;
import com.jujeol.feedback.domain.usecase.command.PreferenceUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PreferenceCommandExecutor implements
    PreferenceRegisterUseCase,
    PreferenceUpdateUseCase,
    PreferenceDeleteUseCase {

    private final PreferenceRegisterUseCase.PreferencePort registerPreferencePort;
    private final PreferenceUpdateUseCase.PreferencePort updatePreferencePort;
    private final PreferenceDeleteUseCase.PreferencePort deletePreferencePort;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void register(PreferenceRegisterCommand command) {
        if (registerPreferencePort.existsByMemberIdAndDrinkId(command.getMemberId(), command.getDrinkId())) {
            throw new DuplicatePreferenceException();
        }
        registerPreferencePort.insert(command.getMemberId(), command.getDrinkId(), command.getPreferenceRate());
        eventPublisher.publishEvent(PreferenceEvent.create(EventType.CREATE, command.getPreferenceRate(), command.getDrinkId()));
    }

    @Override
    @Transactional
    public void update(PreferenceUpdateCommand command) {
        Preference preference = updatePreferencePort.findByDrinkIdAndMemberId(command.getDrinkId(), command.getMemberId())
            .orElseThrow(PreferenceNotExistException::new);

        updatePreferencePort.update(preference.getId(), command.getPreferenceRate());
        eventPublisher.publishEvent(PreferenceEvent.create(EventType.UPDATE, command.getPreferenceRate(), command.getDrinkId()));
    }

    @Override
    @Transactional
    public void delete(PreferenceDeleteCommand command) throws PreferenceNotExistException {
        Preference preference = deletePreferencePort.findByDrinkIdAndMemberId(command.getDrinkId(), command.getMemberId())
            .orElseThrow(PreferenceNotExistException::new);

        if (!preference.getMemberId().equals(command.getMemberId())) {
            throw new NotAuthorizedActionException();
        }

        deletePreferencePort.delete(command.getDrinkId());
        eventPublisher.publishEvent(PreferenceEvent.create(EventType.DELETE, 0, command.getDrinkId()));
    }
}
