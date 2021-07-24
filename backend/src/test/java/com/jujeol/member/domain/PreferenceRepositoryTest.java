package com.jujeol.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
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
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Drink savedDrink;
    private Member savedMember;
    private Category BEER;

    @BeforeEach
    void setUp() {
        BEER = categoryRepository.save(Category.create("맥주"));
        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", BEER);
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

    @Test
    void drinksByPreferenceAvgTest() {
        //given
        Member member = Member.from(Provider.of("5678", ProviderName.TEST));
        Member savedMember2 = memberRepository.save(member);
        Drink apple = Drink.create(
                "애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", BEER);
        Drink savedDrink2 = drinkRepository.save(apple);

        Preference preference1 = Preference.from(savedMember, savedDrink, 2.0);
        Preference preference2 = Preference.from(savedMember2, savedDrink, 4.0);
        preferenceRepository.save(preference1);
        preferenceRepository.save(preference2);
        preference1 = Preference.from(savedMember, savedDrink2, 2.0);
        preferenceRepository.save(preference1);

        Review review1 = Review.create("아주 맛있네요1", savedDrink2, savedMember);
        Review review2 = Review.create("아주 맛있네요2", savedDrink2, savedMember2);
        Review review3 = Review.create("아주 맛있네요3", savedDrink, savedMember);
        Review saveReview1 = reviewRepository.save(review1);
        Review saveReview2 = reviewRepository.save(review2);
        Review saveReview3 = reviewRepository.save(review3);

        savedDrink2.addReview(saveReview1);
        savedDrink2.addReview(saveReview2);
        savedDrink.addReview(saveReview3);

        Pageable pageable = PageRequest.of(0, 10);

        testEntityManager.flush();
        testEntityManager.clear();

        //when
        List<Drink> drinks = preferenceRepository.findAllOrderByPreference(pageable)
                .stream()
                .collect(Collectors.toList());

        //then
        assertThat(drinks.get(0).getName()).isEqualTo("스텔라");
    }
}
