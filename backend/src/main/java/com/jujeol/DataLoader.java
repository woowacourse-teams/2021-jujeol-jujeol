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
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
import com.jujeol.member.domain.nickname.Nickname;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
public class DataLoader implements CommandLineRunner {

    private final DrinkRepository drinkRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(
            DrinkRepository drinkRepository,
            ReviewRepository reviewRepository,
            MemberRepository memberRepository,
            CategoryRepository categoryRepository
    ) {
        this.drinkRepository = drinkRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Category BEER = categoryRepository.save(Category.create("맥주", "BEER"));
        Category SOJU = categoryRepository.save(Category.create("소주", "SOJU"));
        Category WINE = categoryRepository.save(Category.create("와인", "WINE"));
        Category MAKGEOLLI = categoryRepository.save(Category.create("막걸리", "MAKGEOLLI"));
        Category YANGJU = categoryRepository.save(Category.create("양주", "YANGJU"));
        Category COCKTAIL = categoryRepository.save(Category.create("칵테일", "COCKTAIL"));
        Category ETC = categoryRepository.save(Category.create("기타", "ETC"));

        // Drink Data
        Drink stella = Drink.create("스텔라", "stella", 5.5, "w_400/stella_artois_w400.png", 0.0, BEER);
        Drink kgb = Drink.create("KGB", "", 3.5, "w_400/kgb_w400.png", 0.0, BEER);
        Drink efes = Drink.create("EFES", "",7.5, "w_400/efes_w400.png", 0.0, BEER);
        Drink tiger_rad = Drink.create("타이거 라들러 자몽", "Tiger_Rad", 9.5, "w_400/tiger_raddler_grapefruit_w400.png", 0.0, BEER);
        Drink tsingtao = Drink.create("칭따오", "TSINGTAO", 12.0, "w_400/tsingtao_w400.png", 0.0, BEER);
        Drink gom_pyo = Drink.create("곰표", "gom_pyo", 8.2, "w_400/gom_pyo_w400.png", 0.0, BEER);
        Drink ob = Drink.create("오비", "OB", 5.5, "w_400/ob_lager_w400.png", 0.0, BEER);
        Drink tigerLemon = Drink.create("타이거 라들러 레몬", "Tiger_Lemon", 4.5, "w_400/tiger_raddler_lemon_w400.png", 0.0, BEER);

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);

        // Member Data
        Provider provider = Provider.of("1234", ProviderName.TEST);
        Member member = Member.create(provider, Nickname.create("익명의_인물"), Biography.create("익명이라는 의미의 익명의 사람"));

        memberRepository.save(member);

        // Review Data
        reviewRepository.save(Review.create("정말 맛있어요! - 소롱", stella, member));
        reviewRepository.save(Review.create("평범해요 - 크로플", stella, member));
        reviewRepository.save(Review.create("전 이건 좀.. - 나봄", stella, member));
        reviewRepository.save(Review.create("ㅋㅋ 리뷰 - 웨지", stella, member));
        reviewRepository.save(Review.create("너무 비싸요 - 피카", stella, member));
        reviewRepository.save(Review.create("내가 대장이다 - 서니", stella, member));
        reviewRepository.save(Review.create("나는 행운의 여신 - 티케", stella, member));
        reviewRepository.save(Review.create("나는 프의백 - 소롱", stella, member));
        reviewRepository.save(Review.create("배고파 - 피카", stella, member));
        reviewRepository.save(Review.create("오늘도 멋진하루 - 웨지", stella, member));
        reviewRepository.save(Review.create("멀티 모듈 - 나봄", stella, member));
        reviewRepository.save(Review.create("정말 맛있어요! - 소롱", stella, member));

        reviewRepository.save(Review.create("난 너무 예뼈 - 소롱", kgb, member));
        reviewRepository.save(Review.create("나도 이뻐 - 티케", kgb, member));
        reviewRepository.save(Review.create(" ㅋ - 서니", kgb, member));
        reviewRepository.save(Review.create("정말 맛있어요! - 소롱", kgb, member));
    }
}
