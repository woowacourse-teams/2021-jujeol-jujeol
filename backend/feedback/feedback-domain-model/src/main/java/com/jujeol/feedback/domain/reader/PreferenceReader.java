package com.jujeol.feedback.domain.reader;

import com.jujeol.feedback.domain.model.Preference;

import java.util.Optional;

public interface PreferenceReader {

    Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId);
}
