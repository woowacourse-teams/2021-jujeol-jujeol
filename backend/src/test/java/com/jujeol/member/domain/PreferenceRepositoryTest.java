package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private CategoryRepository categoryRepository;

    private Drink savedDrink;
    private Member savedMember;
    private Member savedMember2;
    private Category BEER;

    @BeforeEach
    void setUp() {

        Category BEER = categoryRepository.save(Category.create("맥주"));
        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        savedDrink = drinkRepository.save(stella);

        Member member = Member.create(Provider.of("1234", ProviderName.TEST), null, null);
        savedMember = memberRepository.save(member);
        Member member2 = Member.create(Provider.of("1234", ProviderName.TEST), null, null);
        savedMember2 = memberRepository.save(member2);
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

    @DisplayName("선호도 평균 - 성공")
    @Test
    void averageOfPreferenceRate() {
        //given
        Preference preference1 = Preference.from(savedMember, savedDrink, 2.0);
        preferenceRepository.save(preference1);
        Preference preference2 = Preference.from(savedMember2, savedDrink, 4.0);
        preferenceRepository.save(preference2);
        //when
        //then
        assertThat(preferenceRepository.averageOfPreferenceRate(savedDrink.getId()).get())
                .isEqualTo(3.0);
    }
}
