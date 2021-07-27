package com.jujeol.drink.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
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
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private Drink savedDrink;
    private Member savedMember;
    private Category BEER;

    @BeforeEach
    void setUp() {
        BEER = categoryRepository.save(Category.create("맥주", "BEER"));
        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        savedDrink = drinkRepository.save(stella);

        Member member = Member.create(Provider.of("1234", ProviderName.TEST), null, null);
        savedMember = memberRepository.save(member);
    }

    @Test
    void findAllOrderByViewCountTest() {
        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink kgb = Drink.create(
                "KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", 0.0, BEER);
        Drink saveStella = drinkRepository.save(stella);
        Drink saveKgb = drinkRepository.save(kgb);

        saveStella.updateViewCount();
        saveStella.updateViewCount();
        saveStella.updateViewCount();
        saveKgb.updateViewCount();

        testEntityManager.flush();
        testEntityManager.clear();

        Pageable pageable = PageRequest.of(0, 10);

        List<Drink> drinks = drinkRepository.findAllOrderByViewCount(pageable)
                .stream()
                .collect(Collectors.toList());

        assertThat(drinks.get(0)).isEqualTo(saveStella);
        assertThat(drinks.get(0).getViewCount().getViewCount()).isEqualTo(3L);
        assertThat(drinks.get(1)).isEqualTo(saveKgb);
        assertThat(drinks.get(1).getViewCount().getViewCount()).isEqualTo(1L);
    }

    @Test
    void drinksByPreferenceAvgTest() {
        //given
        Member member = Member.create(Provider.of("5678", ProviderName.TEST), null, null);
        Member savedMember2 = memberRepository.save(member);
        Drink apple = Drink.create(
                "애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", 0.0, BEER);
        Drink savedDrink2 = drinkRepository.save(apple);

        Preference preference1 = Preference.from(savedMember, savedDrink, 2.0);
        savedDrink.updateAverage(2.0);
        Preference preference2 = Preference.from(savedMember2, savedDrink, 4.0);
        savedDrink.updateAverage(4.0);
        preferenceRepository.save(preference1);
        preferenceRepository.save(preference2);
        preference1 = Preference.from(savedMember, savedDrink2, 5.0);
        savedDrink2.updateAverage(5.0);
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
        List<Drink> drinks = drinkRepository.findAllOrderByPreferenceAvg(pageable)
                .stream()
                .collect(Collectors.toList());

        //then
        assertThat(drinks.get(0).getName()).isEqualTo(savedDrink2.getName());
    }
}
