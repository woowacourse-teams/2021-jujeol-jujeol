package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.exception.PreferenceNotExistException;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import com.jujeol.feedback.domain.usecase.PreferenceDeleteUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceRegisterUseCase;
import com.jujeol.feedback.domain.usecase.PreferenceUpdateUseCase;
import com.jujeol.feedback.rds.entity.PreferenceEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PreferenceDomainRepository implements
    PreferenceReader,

    PreferenceRegisterUseCase.PreferencePort,
    PreferenceUpdateUseCase.PreferencePort,
    PreferenceDeleteUseCase.PreferencePort {

    private final PreferenceJpaRepository preferenceJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId) {
        return preferenceJpaRepository.findByDrinkIdAndMemberId(drinkId, memberId).map(PreferenceEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preference> findByMemberIdAndDrinkIdIn(Long memberId, List<Long> drinkIds) {
        return preferenceJpaRepository.findByMemberIdAndDrinkIdIn(memberId, drinkIds)
            .stream()
            .map(PreferenceEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Preference> findByDrinkId(Long drinkId) {
        return preferenceJpaRepository.findAllByDrinkId(drinkId);
    }

    @Override
    @Transactional
    public void insert(Long memberId, Long drinkId, double rate) {
        PreferenceEntity preferenceEntity = PreferenceEntity.builder()
            .memberId(memberId)
            .drinkId(drinkId)
            .rate(rate)
            .build();
        preferenceJpaRepository.save(preferenceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMemberIdAndDrinkId(Long memberId, Long drinkId) {
        return preferenceJpaRepository.findByDrinkIdAndMemberId(drinkId, memberId).isPresent();
    }

    @Override
    @Transactional
    public void update(Long id, double rate) {
        preferenceJpaRepository.findById(id)
            .orElseThrow(PreferenceNotExistException::new)
            .updateRate(rate);
    }

    @Override
    @Transactional
    public void delete(Long drinkId) {
        preferenceJpaRepository.deleteById(drinkId);
    }
}
