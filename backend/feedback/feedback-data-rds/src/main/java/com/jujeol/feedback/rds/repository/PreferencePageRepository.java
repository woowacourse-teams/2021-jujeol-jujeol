package com.jujeol.feedback.rds.repository;

import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.rds.entity.PreferenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Deprecated // app 쪽에서 page 관련 의존성이 빠질 때가지만 잠시 사용
@Component
@RequiredArgsConstructor
public class PreferencePageRepository {

    private final PreferenceJpaRepository preferenceJpaRepository;

    @Transactional(readOnly = true)
    public Page<Preference> findByMemberId(Long memberId, Pageable pageable) {
        return preferenceJpaRepository.findByMemberId(memberId, pageable).map(PreferenceEntity::toDomain);
    }
}
