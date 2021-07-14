package com.jujeol;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.ReviewRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test"})
public class TestDataLoader implements CommandLineRunner {

    private DrinkRepository drinkRepository;
    private ReviewRepository reviewRepository;

    public static List<Drink> BEERS;
    public static List<Review> REVIEWS;

    public TestDataLoader(DrinkRepository drinkRepository, ReviewRepository reviewRepository) {
        this.drinkRepository = drinkRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Drink stella = Drink.from(
                "스텔라", "stella",5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink kgb = Drink.from(
                "KGB", null,3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", Category.BEER);
        Drink estp = Drink.from(
                "ESTP", null, 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", Category.BEER);
        Drink tiger_rad = Drink.from(
                "타이거 라들러 자몽", "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png", Category.BEER);
        Drink tsingtao = Drink.from(
                "칭따오", "TSINGTAO", 12.0, "KakaoTalk_Image_2021-07-08-19-58-18_005.png", Category.BEER);
        Drink apple = Drink.from(
                "애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", Category.BEER);
        Drink ob = Drink.from(
                "오비", "OB", 85.0, "KakaoTalk_Image_2021-07-08-19-58-22_007.png", Category.BEER);
        Drink tigerLemon = Drink.from(
                "타이거 라들러 레몬", "Tiger_Lemon", 4.5, "KakaoTalk_Image_2021-07-08-19-58-22_008.png", Category.BEER);

        List<Drink> beers = Arrays.asList(
                stella, kgb, estp, tiger_rad, tsingtao, apple, ob, tigerLemon);

        BEERS = drinkRepository.saveAll(beers);

        Review review1 = Review.from("천재 윤피카", stella);
        Review review2 = Review.from("천재 크로플", stella);
        Review review3 = Review.from("천재 나봄", stella);
        Review review4 = Review.from("천재 웨지", stella);

        List<Review> reviews = List.of(review1, review2, review3, review4);

        REVIEWS = reviewRepository.saveAll(reviews);
    }
}
