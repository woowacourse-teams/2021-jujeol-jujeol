package com.jujeol;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.member.domain.Biography;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
import com.jujeol.member.domain.nickname.Nickname;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile({"test"})
public class TestDataLoader {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final CategoryRepository categoryRepository;

    public static List<Drink> BEERS;
    public static List<Review> REVIEWS;
    public static Member MEMBER;
    public static Member MEMBER2;
    public static Preference PREFERENCE;
    public static Category BEER_CATEGORY;
    public static Category SOJU_CATEGORY;

    public TestDataLoader(DrinkRepository drinkRepository,
            ReviewRepository reviewRepository,
            MemberRepository memberRepository,
            PreferenceRepository preferenceRepository,
            CategoryRepository categoryRepository) {
        this.drinkRepository = drinkRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.preferenceRepository = preferenceRepository;
        this.categoryRepository = categoryRepository;
    }

//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//
//        saveCategory();
//
//        Drink stella = Drink.create(
//                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 0.0,
//                BEER_CATEGORY);
//        Drink kgb = Drink.create(
//                "KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", 0.0, BEER_CATEGORY);
//        Drink estp = Drink.create(
//                "ESTP", "", 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", 0.0, BEER_CATEGORY);
//        Drink tiger_rad = Drink.create(
//                "타이거 라들러 자몽", "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png", 0.0,
//                BEER_CATEGORY);
//        Drink tsingtao = Drink.create(
//                "칭따오", "TSINGTAO", 12.0, "KakaoTalk_Image_2021-07-08-19-58-18_005.png", 0.0,
//                BEER_CATEGORY);
//        Drink apple = Drink.create(
//                "애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", 0.0,
//                BEER_CATEGORY);
//        Drink ob = Drink.create(
//                "오비", "OB", 85.0, "KakaoTalk_Image_2021-07-08-19-58-22_007.png", 0.0,
//                BEER_CATEGORY);
//        Drink tigerLemon = Drink.create(
//                "타이거 라들러 레몬", "Tiger_Lemon", 4.5, "KakaoTalk_Image_2021-07-08-19-58-22_008.png",
//                0.0,
//                BEER_CATEGORY);
//
//        List<Drink> beers = Arrays.asList(
//                stella, kgb, estp, tiger_rad, tsingtao, apple, ob, tigerLemon);
//
//        BEERS = drinkRepository.saveAll(beers);
//
//        Provider provider1 = Provider.of("1234", ProviderName.TEST);
//        Member member1 = Member
//                .create(provider1, Nickname.create("아무닉네임"), Biography.create("아무자기소개"));
//        Provider provider2 = Provider.of("5678", ProviderName.TEST);
//        Member member2 = Member
//                .create(provider2, Nickname.create("아무닉네임2"), Biography.create("아무자기소개"));
//
//        MEMBER = memberRepository.save(member1);
//        MEMBER2 = memberRepository.save(member2);
//
//        PREFERENCE = preferenceRepository.save(Preference.from(MEMBER, stella, 5));
//        stella.updateAverage(5.0);
//        preferenceRepository.save(Preference.from(MEMBER, kgb, 4.5));
//        kgb.updateAverage(4.5);
//        preferenceRepository.save(Preference.from(MEMBER, estp, 4));
//        estp.updateAverage(4.0);
//        preferenceRepository.save(Preference.from(MEMBER2, tiger_rad, 3.5));
//        tiger_rad.updateAverage(3.5);
//        preferenceRepository.save(Preference.from(MEMBER2, tsingtao, 3));
//        tsingtao.updateAverage(3.0);
//        preferenceRepository.save(Preference.from(MEMBER2, apple, 2.5));
//        apple.updateAverage(2.5);
//        preferenceRepository.save(Preference.from(MEMBER2, ob, 2));
//        ob.updateAverage(2.0);
//        preferenceRepository.save(Preference.from(MEMBER2, tigerLemon, 1.5));
//        tigerLemon.updateAverage(1.5);
//
//        Review review1 = Review.create("천재 윤피카", stella, MEMBER);
//        Review review2 = Review.create("천재 크로플", stella, MEMBER);
//        Review review3 = Review.create("천재 나봄", stella, MEMBER);
//        Review review4 = Review.create("천재 소롱", stella, MEMBER);
//        Review review5 = Review.create("천재 소롱", stella, MEMBER);
//        Review review6 = Review.create("천재 소롱", stella, MEMBER);
//        Review review7 = Review.create("천재 소롱", stella, MEMBER);
//        Review review8 = Review.create("천재 소롱", stella, MEMBER);
//        Review review9 = Review.create("천재 소롱", stella, MEMBER);
//        Review review10 = Review.create("바보 피카", stella, MEMBER2);
//        Review review11 = Review.create("바보 피카", stella, MEMBER2);
//        Review review12 = Review.create("바보 피카", stella, MEMBER2);
//        Review review13 = Review.create("이제.. 끝낼래...", estp, MEMBER);
//
//        List<Review> reviews = List.of(
//                review1, review2, review3, review4,
//                review5, review6, review7, review8,
//                review9, review10, review11, review12,
//                review13
//        );
//
//        REVIEWS = reviewRepository.saveAll(reviews);
//    }
//
//    public void saveCategory() {
//        BEER_CATEGORY = categoryRepository.save(Category.create("맥주", "BEER"));
//        SOJU_CATEGORY = categoryRepository.save(Category.create("소주", "SOJU"));
//    }
//
//    public void removeAll() {
//        reviewRepository.deleteAll();
//        preferenceRepository.deleteAll();
//        drinkRepository.deleteAll();
//        memberRepository.deleteAll();
//        categoryRepository.deleteAll();
//    }
}
