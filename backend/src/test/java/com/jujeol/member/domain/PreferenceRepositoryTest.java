package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.commons.config.QuerydslConfig;
import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.ImageFilePath;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.member.auth.domain.Provider;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.repository.PreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(value = QuerydslConfig.class)
@DataJpaTest
public class PreferenceRepositoryTest {

    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Drink savedDrink;
    private Member savedMember;
    private Member savedMember2;
    private Category BEER;

    @BeforeEach
    void setUp() {
        Category BEER = categoryRepository.save(Category.create("맥주", "BEER"));
        ImageFilePath imageFilePath = ImageFilePath.create(
            "test_w200.png",
                "test_w400.png",
                "test_w600.png"
        );

        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, imageFilePath, 0.0, BEER, "아아 이것은 맥주라는 것이다.");
        savedDrink = drinkRepository.save(stella);

        Member member = Member.create(Provider.create("1234", ProviderName.TEST), null, null);
        savedMember = memberRepository.save(member);
        Member member2 = Member.create(Provider.create("1234", ProviderName.TEST), null, null);
        savedMember2 = memberRepository.save(member2);
    }

    @Test
    @DisplayName("memberId와 drinkId를 통한 조회 테스트")
    public void findByMemberIdAndDrinkIdTest() {
        //given
        Preference preference = Preference.create(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        Preference actual = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.create(savedMember, savedDrink, 0.0));

        //then
        assertThat(savedPreference.getRate()).isEqualTo(actual.getRate());
    }

    @Test
    @DisplayName("선호도 수정 테스트")
    public void updateTest() {
        //given
        Preference preference = Preference.create(savedMember, savedDrink, 2.0);
        Preference savedPreference = preferenceRepository.save(preference);

        //when
        savedPreference.updateRate(4.0);
        Preference findPreference = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.create(savedMember, savedDrink, 0.0));

        //then
        assertThat(findPreference.getRate()).isEqualTo(4.0);
    }

    @Test
    @DisplayName("memberId와 drinkId를 통한 삭제 테스트")
    public void deleteByMemberIdAndDrinkIdTest() {
        //given
        Preference preference = Preference.create(savedMember, savedDrink, 3.0);
        preferenceRepository.save(preference);

        //when
        preferenceRepository
                .deleteByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId());
        Preference expect = preferenceRepository
                .findByMemberIdAndDrinkId(savedMember.getId(), savedDrink.getId())
                .orElseGet(() -> Preference.create(savedMember, savedDrink, 0.0));

        //then
        assertThat(expect.getRate()).isEqualTo(0.0);
    }

    @DisplayName("선호도 평균 - 성공")
    @Test
    void averageOfPreferenceRate() {
        //given
        Preference preference1 = Preference.create(savedMember, savedDrink, 2.0);
        preferenceRepository.save(preference1);
        Preference preference2 = Preference.create(savedMember2, savedDrink, 4.0);
        preferenceRepository.save(preference2);
        //when
        //then
        assertThat(preferenceRepository.averageOfPreferenceRate(savedDrink.getId()).get())
                .isEqualTo(3.0);
    }
}
