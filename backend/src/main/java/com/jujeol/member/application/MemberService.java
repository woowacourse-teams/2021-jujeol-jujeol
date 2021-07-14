package com.jujeol.member.application;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.application.dto.PreferenceRequest;
import com.jujeol.member.domain.LoginMember;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final DrinkRepository drinkRepository;

    public MemberResponse findMember(LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NoSuchMemberException::new);
        return MemberResponse.of(member);
    }

    @Transactional
    public void createOrUpdatePreference(
            LoginMember loginMember,
            Long drinkId,
            PreferenceRequest preferenceRequest
    ) {
        Member member = memberRepository.findById(loginMember.getId())
                .orElseThrow(NoSuchMemberException::new);
        Drink drink = drinkRepository.findById(drinkId)
                .orElseThrow(IllegalArgumentException::new);

        preferenceRepository
                .findByMemberIdAndDrinkId(member.getId(), drink.getId())
                .ifPresentOrElse(exist -> exist.updateRate(preferenceRequest.getRate()),
                        () -> {
                            Preference newPreference = Preference
                                    .from(member, drink, preferenceRequest.getRate());
                            preferenceRepository.save(newPreference);
                        }
                );
    }

    @Transactional
    public void deletePreference(LoginMember loginMember, Long drinkId) {
        preferenceRepository.deleteByMemberIdAndDrinkId(loginMember.getId(), drinkId);
    }
}
