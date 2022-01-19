package com.jujeol.preference.domain.repository;

import com.jujeol.preference.domain.Preference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PreferenceCustomRepository {

    Optional<Double> averageOfPreferenceRate(Long drinkId);

    Page<Preference> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

    List<Preference> findAllByCategory(String category);
}
