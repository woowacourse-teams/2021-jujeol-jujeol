package com.jujeol.member.application;

import com.jujeol.drink.application.dto.DrinkDto;
import com.jujeol.drink.application.dto.ReviewDto;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.PreferenceDto;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.exception.NoSuchMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    @Value("${file-server.url:}")
    private String fileServerUrl;

    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;

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

    public Page<DrinkDto> findDrinks(Long id, Pageable pageable) {
        return drinkRepository.findAll(pageable)
                .map(drink -> DrinkDto.from(
                        drink,
                        Preference.from(drink, 3.5),
                        fileServerUrl
                        )
                );
    }

    public Page<ReviewDto> findReviews(Long id, Pageable pageable) {
        return reviewRepository.findAll(pageable)
                .map(review -> ReviewDto.create(
                        review.getId(),
                        DrinkDto.from(
                                review.getDrink(),
                                Preference.from(review.getDrink(), 3.5),
                                fileServerUrl
                        ),
                        review.getContent(),
                        review.getCreatedAt(),
                        review.getModifiedAt()
                ));
    }
}
