package com.jujeol.member.application;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final DrinkRepository drinkRepository;

    public MemberDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NoSuchMemberException::new);
        return MemberDto.from(member);
    }

    @Transactional
    public void createOrUpdatePreference(
            Long memberId,
            Long drinkId,
            PreferenceDto preferenceDto
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(NotFoundDrinkException::new);

        preferenceRepository
                .findByMemberIdAndDrinkId(member.getId(), drink.getId())
                .ifPresentOrElse(exist -> exist.updateRate(preferenceDto.getPreferenceRate()),
                        () -> {
                            Preference newPreference = Preference
                                    .from(member, drink, preferenceDto.getPreferenceRate());
                            preferenceRepository.save(newPreference);
                        }
                );
    }

    @Transactional
    public void deletePreference(Long memberId, Long drinkId) {
        preferenceRepository.deleteByMemberIdAndDrinkId(memberId, drinkId);
    }
}
