package com.jujeol;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Preference;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test"})
public class TestDataLoader implements CommandLineRunner {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;

    public static List<Drink> BEERS;
    public static List<Review> REVIEWS;
    public static Member MEMBER;
    public static Member MEMBER2;
    public static Preference PREFERENCE;

    public TestDataLoader(
            DrinkRepository drinkRepository,
            ReviewRepository reviewRepository,
            MemberRepository memberRepository,
            PreferenceRepository preferenceRepository
    ) {
        this.drinkRepository = drinkRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Drink stella = Drink.from(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink kgb = Drink.from(
                "KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", Category.BEER);
        Drink estp = Drink.from(
                "ESTP", "", 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", Category.BEER);
        Drink tiger_rad = Drink.from(
                "타이거 라들러 자몽", "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png",
                Category.BEER);
        Drink tsingtao = Drink.from(
                "칭따오", "TSINGTAO", 12.0, "KakaoTalk_Image_2021-07-08-19-58-18_005.png",
                Category.BEER);
        Drink apple = Drink.from(
                "애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", Category.BEER);
        Drink ob = Drink.from(
                "오비", "OB", 85.0, "KakaoTalk_Image_2021-07-08-19-58-22_007.png", Category.BEER);
        Drink tigerLemon = Drink.from(
                "타이거 라들러 레몬", "Tiger_Lemon", 4.5, "KakaoTalk_Image_2021-07-08-19-58-22_008.png",
                Category.BEER);

        List<Drink> beers = Arrays.asList(
                stella, kgb, estp, tiger_rad, tsingtao, apple, ob, tigerLemon);

        BEERS = drinkRepository.saveAll(beers);

        Provider provider1 = Provider.of("1234", ProviderName.TEST);
        Member member1 = Member.create(provider1, null, null);
        Provider provider2 = Provider.of("5678", ProviderName.TEST);
        Member member2 = Member.create(provider2, null, null);

//        MEMBER = memberRepository.save(member1);
//        MEMBER2 = memberRepository.save(member2);
//
//        PREFERENCE = preferenceRepository.save(Preference.from(MEMBER, stella, 3.5));
//
//        Review review1 = Review.from("천재 윤피카", stella, MEMBER);
//        Review review2 = Review.from("천재 크로플", stella, MEMBER);
//        Review review3 = Review.from("천재 나봄", stella, MEMBER);
//        Review review4 = Review.from("천재 소롱", stella, MEMBER);
//        Review review5 = Review.from("천재 소롱", stella, MEMBER);
//        Review review6 = Review.from("천재 소롱", stella, MEMBER);
//        Review review7 = Review.from("천재 소롱", stella, MEMBER);
//        Review review8 = Review.from("천재 소롱", stella, MEMBER);
//        Review review9 = Review.from("천재 소롱", stella, MEMBER);
//        Review review10 = Review.from("바보 피카", stella, MEMBER2);
//        Review review11 = Review.from("바보 피카", stella, MEMBER2);
//        Review review12 = Review.from("바보 피카", stella, MEMBER2);
//        Review review13 = Review.from("이제.. 끝낼래...", estp, MEMBER);
//
//        List<Review> reviews = List.of(
//                review1, review2, review3, review4,
//                review5, review6, review7, review8,
//                review9, review10, review11, review12,
//                review13
//        );
//
//        REVIEWS = reviewRepository.saveAll(reviews);
    }

    public void removeAll() {
        reviewRepository.deleteAll();
        preferenceRepository.deleteAll();
        drinkRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
