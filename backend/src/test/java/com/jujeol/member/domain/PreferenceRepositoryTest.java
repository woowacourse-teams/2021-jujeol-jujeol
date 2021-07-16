package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.member.exception.NoSuchPreferenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PreferenceRepositoryTest {

    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Drink savedDrink;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        savedDrink = drinkRepository.save(stella);

        Member member = Member.from(Provider.of("1234", ProviderName.TEST));
        savedMember = memberRepository.save(member);
    }

    @Test
    @DisplayName("memberId와 drinkId를 통한 조회 테스트")
    public void findByMemberIdAndDrinkIdTest() {
        //given
        Preference preference = Preference.of(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        Preference actual = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseThrow(NoSuchPreferenceException::new);

        //then
        assertThat(savedPreference.getRate()).isEqualTo(actual.getRate());
    }

    @Test
    @DisplayName("선호도 수정 테스트")
    public void updateTest() {
        //given
        Preference preference = Preference.of(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        savedPreference.updateRate(4.0);
        Preference findPreference = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseThrow(NoSuchPreferenceException::new);
        //then
        assertThat(findPreference.getRate()).isEqualTo(4.0);
    }

    @Test
    @DisplayName("memberId와 drinkId를 통한 삭제 테스트")
    public void deleteByMemberIdAndDrinkIdTest() {
        //given
        Preference preference = Preference.of(savedMember, savedDrink, 3.0);
        preferenceRepository.save(preference);

        //when
        preferenceRepository
                .deleteByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId());

        //then
        assertThatThrownBy(() -> {
            preferenceRepository
                    .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                    .orElseThrow(NoSuchPreferenceException::new);
        }).isInstanceOf(NoSuchPreferenceException.class)
                .hasMessageContaining("해당 주류에 대한 선호도가 없습니다");
    }
}
