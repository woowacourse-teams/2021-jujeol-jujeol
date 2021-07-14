package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.drink.exception.NotFoundDrinkException;
import com.jujeol.drink.exception.NotFoundReviewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("review와 drink 연관관계 매핑이 잘 되는지 테스트")
    @Test
    void saveDrinkAndReview() {
        //given
        Drink stella = Drink.from(
            "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella);
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
            "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella);
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

    @DisplayName("")
    @Test
    public void update(){
        //given
        Drink stella = Drink.from(
            "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink saveDrink = drinkRepository.save(stella);

        Review review = Review.from("아주 맛있네요!", stella);
        Review saveReview = reviewRepository.save(review);

        saveDrink.addReview(saveReview);

        //when
        review.editContent("사실은 맛없어요.");
        testEntityManager.flush();
        testEntityManager.clear();

        //then
        Review editReview = reviewRepository.findById(saveReview.getId()).orElseThrow(NotFoundReviewException::new);
        assertThat(editReview.getContent()).isEqualTo("사실은 맛없어요.");
    }
}
