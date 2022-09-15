package com.jujeol.feedback.domain.reader;

import com.jujeol.feedback.domain.model.Preference;
import java.util.List;
import java.util.Optional;

public interface PreferenceReader {

    Optional<Preference> findByDrinkIdAndMemberId(Long drinkId, Long memberId);

    List<Preference> findByMemberIdAndDrinkIdIn(Long memberId, List<Long> drinkIds);

    List<Preference> findByDrinkId(Long drinkId);
}
