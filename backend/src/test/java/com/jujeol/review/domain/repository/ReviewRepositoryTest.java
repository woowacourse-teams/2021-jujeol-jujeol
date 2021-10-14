package com.jujeol.review.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.TestConfig;
import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.ImageFilePath;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.drink.exception.NotFoundDrinkException;
import com.jujeol.member.auth.domain.Provider;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.member.domain.Biography;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.nickname.Nickname;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.review.domain.Review;
import com.jujeol.review.exception.NotFoundReviewException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Member member;
    private Category BEER;
    private Drink stella;

    @BeforeEach
    void setUp() {
        BEER = categoryRepository.save(Category.create("맥주", "BEER"));
        Member createMember = Member.create(Provider.create("1234", ProviderName.TEST),
            Nickname.create("주류의신소롱"),
            Biography.create("누가 날 막을쏘냐"));
        member = memberRepository.save(createMember);

        ImageFilePath imageFilePath = ImageFilePath.create(
            "test_w200.png",
            "test_w400.png",
            "test_w600.png"
        );

        stella = Drink.create(
            "스텔라", "stella", 5.5, imageFilePath, 0.0, BEER, "아아 이것은 맥주라는 것이다.");
    }

    @DisplayName("Review와 Drink 연관관계 매핑이 잘 되는지 테스트")
    @Test
    void saveDrinkAndReview() {
        //given
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.create("아주 맛있네요!", stella, member);
        Review saveReview = reviewRepository.save(review);

        saveDrink.addReview(saveReview);
        //when
        Drink findDrink = drinkRepository.findById(saveDrink.getId())
            .orElseThrow(NotFoundDrinkException::new);
        Review findReview = reviewRepository.findById(saveReview.getId())
            .orElseThrow(IllegalArgumentException::new);
        //then
        assertThat(findReview.getDrink()).isEqualTo(findDrink);
        assertThat(findDrink.getReviews().get(0)).isEqualTo(findReview);
    }

    @DisplayName("리뷰 삭제 - 성공 (jpa 테스트)")
    @Test
    public void delete() {
        //given
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.create("아주 맛있네요!", stella, member);
        Review saveReview = reviewRepository.save(review);

        saveDrink.addReview(saveReview);

        //when
        reviewRepository.delete(review);
        testEntityManager.flush();
        testEntityManager.clear();

        Drink findDrink = drinkRepository.findById(saveDrink.getId())
            .orElseThrow(NotFoundDrinkException::new);

        // then
        assertThat(findDrink.getReviews()).hasSize(0);
    }

    @DisplayName("Review 업데이트시 Drink와 연관관계 매핑이 잘 되는지 테스트")
    @Test
    public void update() {
        //given
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.create("아주 맛있네요!", stella, member);
        Review saveReview = reviewRepository.save(review);

        saveDrink.addReview(saveReview);

        //when
        review.editContent("사실은 맛없어요.");
        testEntityManager.flush();
        testEntityManager.clear();

        //then
        Review editReview = reviewRepository.findById(saveReview.getId())
            .orElseThrow(NotFoundReviewException::new);
        assertThat(editReview.getContent()).isEqualTo("사실은 맛없어요.");
    }

    @DisplayName("DrinkId로 리뷰 조회 - 성공")
    @Test
    void findAllByDrinkId() {
        //given
        Drink saveDrink = drinkRepository.save(stella);

        Review saveReview1 = reviewRepository.save(Review.create("아주 맛있네요!", stella, member));
        Review saveReview2 = reviewRepository.save(Review.create("평범해요.", stella, member));
        Review saveReview3 = reviewRepository.save(Review.create("이건 좀...", stella, member));

        Pageable pageable = PageRequest.of(0, 10);

        //when
        List<Review> reviews = reviewRepository
            .findAllByDrinkId(saveDrink.getId(), pageable)
            .stream()
            .collect(Collectors.toList());

        //then
        assertThat(reviews).containsExactly(saveReview1, saveReview2, saveReview3);
    }

    @DisplayName("DrinkId와 MemberId로 리뷰 조회 - 성공")
    @Test
    public void findFirstByDrinkIdAndMemberId() {
        //given
        Drink saveDrink = drinkRepository.save(stella);

        Member member2 = Member.create(Provider.create("1234", ProviderName.TEST), null, null);
        Member saveMember2 = memberRepository.save(memberRepository.save(member2));

        Review saveReview1 = reviewRepository.save(Review.create("아주 맛있네요!", saveDrink, member));
        Review saveReview2 = reviewRepository.save(Review.create("평범해요.", saveDrink, member));
        Review saveReview3 = reviewRepository
            .save(Review.create("이건 좀...", saveDrink, saveMember2));

        //when
        List<Review> byDrinkIdAndMemberId = reviewRepository
            .findByDrinkIdAndMemberId(saveDrink.getId(), member.getId(), Pageable.ofSize(1));

        //then
        assertThat(byDrinkIdAndMemberId).containsExactly(saveReview2);
    }
}
