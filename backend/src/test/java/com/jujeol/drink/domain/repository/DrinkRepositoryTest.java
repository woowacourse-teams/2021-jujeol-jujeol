package com.jujeol.drink.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.DataConfig;
import com.jujeol.TestConfig;
import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.ImageFilePath;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.recommend.domain.RecommendationTheme;
import com.jujeol.member.auth.domain.Provider;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.preference.domain.Preference;
import com.jujeol.preference.domain.PreferenceRepository;
import com.jujeol.review.domain.Review;
import com.jujeol.review.domain.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(DataConfig.class)
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
    }

    @Test
    void drinksByPreferenceAvgTest() {
        //given
        Member member = Member.create(Provider.create("5678", ProviderName.TEST), null, null);
        Member savedMember2 = memberRepository.save(member);

        ImageFilePath appleImageFilePath = ImageFilePath
            .create("test_w200.png",
                "test_w400.png",
                "test_w600.png"
            );

        Drink apple = Drink.create(
            "애플", "Apple", 8.2, appleImageFilePath, 0.0, BEER, "아아 이것은 맥주라는 것이다.");
        Drink savedDrink2 = drinkRepository.save(apple);

        Preference preference1 = Preference.create(savedMember, savedDrink, 2.0);
        savedDrink.updateAverage(2.0);
        Preference preference2 = Preference.create(savedMember2, savedDrink, 4.0);
        savedDrink.updateAverage(4.0);
        preferenceRepository.save(preference1);
        preferenceRepository.save(preference2);
        preference1 = Preference.create(savedMember, savedDrink2, 5.0);
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
        List<Drink> drinks = drinkRepository
            .findByRecommendation(RecommendationTheme.PREFERENCE, pageable)
            .stream()
            .collect(Collectors.toList());

        //then
        assertThat(drinks.get(0).getName()).isEqualTo(savedDrink2.getName());
    }
}
