package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.DrinkRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Preference preference = Preference.from(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        Preference actual = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.from(savedMember, savedDrink, 0.0));

        //then
        assertThat(savedPreference.getRate()).isEqualTo(actual.getRate());
    }

    @Test
    @DisplayName("선호도 수정 테스트")
    public void updateTest() {
        //given
        Preference preference = Preference.from(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        savedPreference.updateRate(4.0);
        Preference findPreference = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.from(savedMember, savedDrink, 0.0));
        //then
        assertThat(findPreference.getRate()).isEqualTo(4.0);
    }

    @Test
    @DisplayName("memberId와 drinkId를 통한 삭제 테스트")
    public void deleteByMemberIdAndDrinkIdTest() {
        //given
        Preference preference = Preference.from(savedMember, savedDrink, 3.0);
        preferenceRepository.save(preference);

        //when
        preferenceRepository
                .deleteByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId());
        Preference expect = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.from(savedMember, savedDrink, 0.0));

        //then
        assertThat(expect.getRate()).isEqualTo(0.0);
    }

    @DisplayName("선호도로 내가 마신 술 조회 - 성공")
    @Test
    public void findDrinkUsingPreference(){
        //given
        Drink kgb = Drink.from(
                "KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", Category.BEER);
        Drink estp = Drink.from(
                "ESTP", "", 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", Category.BEER);
        Drink tiger_rad = Drink.from(
                "타이거 라들러 자몽", "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png",
                Category.BEER);

        List<Drink> drinks = drinkRepository.saveAll(List.of(kgb, estp, tiger_rad));

        Preference preference1 = Preference.from(savedMember, kgb, 3.0);
        Preference preference2 = Preference.from(savedMember, estp, 5.0);
        Preference preference3 = Preference.from(savedMember, tiger_rad, 2.5);

        preferenceRepository.saveAll(List.of(preference1, preference2, preference3));

        //when
        Page<Drink> drinkResponses = preferenceRepository
                .findDrinkUsingPreference(savedMember.getId(), Pageable.ofSize(10));

        //then
        List<Long> drinkIds = drinks.stream().map(Drink::getId).collect(Collectors.toList());
        List<Long> actualIds = drinkResponses.stream().map(Drink::getId).collect(Collectors.toList());

        assertThat(drinkResponses).hasSize(drinks.size());
        assertThat(actualIds).isEqualTo(drinkIds);
    }
}
