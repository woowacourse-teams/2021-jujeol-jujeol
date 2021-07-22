package com.jujeol;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.member.domain.nickname.Member;
import com.jujeol.member.domain.nickname.MemberRepository;
import com.jujeol.member.domain.PreferenceRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
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
    private final PreferenceRepository preferenceRepository;

    public DataLoader(DrinkRepository drinkRepository,
            ReviewRepository reviewRepository,
            MemberRepository memberRepository,
            PreferenceRepository preferenceRepository) {
        this.drinkRepository = drinkRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Drink Data
        Drink stella = Drink.from("스텔라", "stella", 5.5, "stella_artois.png", Category.BEER);
        Drink kgb = Drink.from("KGB", "", 3.5, "kgb.png", Category.BEER);
        Drink efes = Drink.from("EFES", "",7.5, "efes.png", Category.BEER);
        Drink tiger_rad = Drink.from("타이거 라들러 자몽", "Tiger_Rad", 9.5, "tiger_raddler_grapefruit.png", Category.BEER);
        Drink tsingtao = Drink.from("칭따오", "TSINGTAO", 12.0, "tsingtao.png", Category.BEER);
        Drink gom_pyo = Drink.from("곰표", "gom_pyo", 8.2, "gom_pyo.png", Category.BEER);
        Drink ob = Drink.from("오비", "OB", 85.0, "ob_lager.png", Category.BEER);
        Drink tigerLemon = Drink.from("타이거 라들러 레몬", "Tiger_Lemon", 4.5, "tiger_raddler_lemon.png", Category.BEER);

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);

        // Member Data
        Provider provider = Provider.of("1234", ProviderName.TEST);
        Member member = Member.createAnonymousMember();

        memberRepository.save(member);

        // Review Data
        reviewRepository.save(Review.from("정말 맛있어요! - 소롱", stella, member));
        reviewRepository.save(Review.from("평범해요 - 크로플", stella, member));
        reviewRepository.save(Review.from("전 이건 좀.. - 나봄", stella, member));
        reviewRepository.save(Review.from("ㅋㅋ 리뷰 - 웨지", stella, member));
        reviewRepository.save(Review.from("너무 비싸요 - 피카", stella, member));
        reviewRepository.save(Review.from("내가 대장이다 - 서니", stella, member));
        reviewRepository.save(Review.from("나는 행운의 여신 - 티케", stella, member));
        reviewRepository.save(Review.from("나는 프의백 - 소롱", stella, member));
        reviewRepository.save(Review.from("배고파 - 피카", stella, member));
        reviewRepository.save(Review.from("오늘도 멋진하루 - 웨지", stella, member));
        reviewRepository.save(Review.from("멀티 모듈 - 나봄", stella, member));
        reviewRepository.save(Review.from("정말 맛있어요! - 소롱", stella, member));

        reviewRepository.save(Review.from("난 너무 예뼈 - 소롱", kgb, member));
        reviewRepository.save(Review.from("나도 이뻐 - 티케", kgb, member));
        reviewRepository.save(Review.from(" ㅋ - 서니", kgb, member));
        reviewRepository.save(Review.from("정말 맛있어요! - 소롱", kgb, member));
    }
}
