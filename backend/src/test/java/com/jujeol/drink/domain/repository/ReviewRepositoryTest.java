package com.jujeol.drink.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.exception.NotFoundReviewException;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
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

    @BeforeEach
    void setUp() {
        BEER = categoryRepository.save(Category.create("맥주"));
        Member createMember = Member.from(Provider.of("1234", ProviderName.TEST));
        member = memberRepository.save(createMember);
    }

    @DisplayName("Review와 Drink 연관관계 매핑이 잘 되는지 테스트")
    @Test
    void saveDrinkAndReview() {
        //given
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella, member);
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
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella, member);
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
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella, member);
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
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review saveReview1 = reviewRepository.save(Review.from("아주 맛있네요!", stella, member));
        Review saveReview2 = reviewRepository.save(Review.from("평범해요.", stella, member));
        Review saveReview3 = reviewRepository.save(Review.from("이건 좀...", stella, member));

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
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0, BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Member member2 = Member.from(Provider.of("1234", ProviderName.TEST));
        Member saveMember2 = memberRepository.save(memberRepository.save(member2));

        Review saveReview1 = reviewRepository.save(Review.from("아주 맛있네요!", stella, member));
        Review saveReview2 = reviewRepository.save(Review.from("평범해요.", stella, member));
        Review saveReview3 = reviewRepository.save(
                Review.from("이건 좀...", stella, saveMember2)
        );

        //when
        List<Review> byDrinkIdAndMemberId = reviewRepository
                .findByDrinkIdAndMemberId(saveDrink.getId(), member.getId(), Pageable.ofSize(1));

        //then
        assertThat(byDrinkIdAndMemberId).containsExactly(saveReview2);
    }
}
