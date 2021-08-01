package com.jujeol;

import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.Review;
import com.jujeol.drink.domain.repository.CategoryRepository;
import com.jujeol.drink.domain.repository.DrinkRepository;
import com.jujeol.drink.domain.repository.ReviewRepository;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.domain.ProviderName;
import java.util.Arrays;
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
        // 첫 데이터
        Drink stella = Drink.create("스텔라", "stella", 5.5, "w_400/stella_artois_w400.png", 0.0, BEER);
        Drink kgb = Drink.create("KGB", "", 3.5, "w_400/kgb_w400.png", 0.0, BEER);
        Drink efes = Drink.create("EFES", "",7.5, "w_400/efes_w400.png", 0.0, BEER);
        Drink tiger_rad = Drink.create("타이거 라들러 자몽", "Tiger_Rad", 9.5, "w_400/tiger_raddler_grapefruit_w400.png", 0.0, BEER);
        Drink tsingtao = Drink.create("칭따오", "TSINGTAO", 12.0, "w_400/tsingtao_w400.png", 0.0, BEER);
        Drink gom_pyo = Drink.create("곰표", "gom_pyo", 8.2, "w_400/gom_pyo_w400.png", 0.0, BEER);
        Drink ob = Drink.create("오비", "OB", 5.5, "w_400/ob_lager_w400.png", 0.0, BEER);
        Drink tigerLemon = Drink.create("타이거 라들러 레몬", "Tiger_Lemon", 4.5, "w_400/tiger_raddler_lemon_w400.png", 0.0, BEER);

        // 스프리드 시트 데이터 추가
        final List<Drink> drinks_spread = Arrays.asList(
                // 맥주
                Drink.create("쥬시후레쉬맥주", "Juicy Fresh Beer", 4.5, "w_400/Juicy_Fresh_Beer_w400.png", 0.0, BEER),
                Drink.create("금성맥주", "Gold Star Beer", 5.1, "w_400/Gold_Star_Beer_w400.png", 0.0, BEER),
                Drink.create("제주 거멍 에일", "Jeju Geomeong Ale", 4.3, "w_400/Jeju_Geomeong_Ale_w400.png", 0.0, BEER),
                Drink.create("에페스 필스너", "EFES Pilsner", 5.0, "w_400/EFES_Pilsner_w400.png", 0.0, BEER),
                Drink.create("오드 괴즈 틸퀸 아렌시아", "Oude Gueze Tilquin a L'Ancienne", 7.0, "Oude_Gueze_Tilquin_a_L'w_400/Ancienne_w400.png", 0.0, BEER),
                Drink.create("제주 백록담 에일", "Jeju Baengnokdam Ale", 4.3, "w_400/Jeju_Baengnokdam_Ale_w400.png", 0.0, BEER),

                // 소주
                Drink.create("시원한청풍소주", "C1Soju", 17.2, "w_400/c1soju_w400.png", 0.0, SOJU),
                Drink.create("참이슬", "Chamisul Soju", 16.9, "w_400/charm-i-sel-soju_w400.png", 0.0, SOJU),
                Drink.create("처음처럼", "cheo-um-cheo-rum Soju", 16.5, "w_400/cheo-um-cheo-rum-soju_w400.png", 0.0, SOJU),
                Drink.create("자몽에이슬", "jamong-eh-i-sul-soju", 13.0, "w_400/jamong-eh-i-sul-soju_w400.png", 0.0, SOJU),
                Drink.create("참이슬 오리지날", "charm-i-sel-soju", 20.1, "w_400/charm-i-sel-original-soju_w400.png", 0.0, SOJU),
                Drink.create("진로", "jinro-soju", 16.5, "w_400/jinro-soju_w400.png", 0.0, SOJU),

                // 와인
                Drink.create("탈로 프리미티보 디 만두리아", "Talo Primitivo Di Manduria", 14.0, "w_400/Talo_Primitivo_Di_Manduria_w400.png", 0.0, WINE),
                Drink.create("킴 크로포드, 피노 그리", "Kim Crawford, Pinot Gris", 13.0, "w_400/Kim_Crawford_Pinot_Gris_w400.png", 0.0, WINE),
                Drink.create("커클랜드 시그니처 토니 포트 10년", "Kirkland Signature Tawny Port 10 years old", 20.0, "w_400/Kirkland_Signature_Tawny_Port_10_years_old_w400.png", 0.0, WINE),
                Drink.create("버니니 클래식", "Bernini Classic", 4.5, "w_400/Bernini_Classic_w400.png", 0.0, WINE),
                Drink.create("T7 까베르네 소비뇽", "T7 Cabernet Sauvignon", 13.0, "w_400/T7_Cabernet_Sauvignon_w400.png", 0.0, WINE),
                Drink.create("복숭아 와인", "Peach Wine", 12.0, "w_400/Peach_Wine_w400.png", 0.0, WINE),
                Drink.create("그란 띠에라 비노 블랑코", "Gran Tierra vino blanco", 10.5, "w_400/Gran_Tierra_vino_blanco_w400.png", 0.0, WINE),
                Drink.create("비냐 란자 틴토", "VINA LANZAR Tinto", 11.5, "w_400/VINA_LANZAR_Tinto_w400.png", 0.0, WINE),
                Drink.create("빈야드 샤도네이", "Vineyards Chardonnay", 13.0, "w_400/Vineyards_Chardonnay_w400.png", 0.0, WINE),

                // 막걸리
                Drink.create("느린마을 막걸리", "Slow City Makgeolli", 6.0, "w_400/Slow_City_Makgeolli_w400.png", 0.0, MAKGEOLLI),
                Drink.create("과천미주", "Gwacheon Mijoo", 9.0, "w_400/Gwacheon_Mijoo_w400.png", 0.0, MAKGEOLLI),
                Drink.create("관악산 생막걸리", "Gwanak Mountain Makgeolli", 6.0, "w_400/Gwanak_Mountain_Makgeolli_w400.png", 0.0, MAKGEOLLI),
                Drink.create("국순당생막걸리", "Guksundang Makgeolli", 6.0, "w_400/Guksundang_Makgeolli_w400.png", 0.0, MAKGEOLLI),
                Drink.create("경주법주 쌀막걸리", "GyeongjuBeopju Rice Makgeolli", 6.0, "w_400/Gyeongju_Beopju_Rice_Makgeolli_w400.png", 0.0, MAKGEOLLI),
                Drink.create("소백산 막걸리", "Sobaeksan Makgeolli", 6.0, "w_400/Sobaeksan_Makgeolli_w400.png", 0.0, MAKGEOLLI),
                Drink.create("대잎 동동주", "Bamboo Leaf Dongdongju", 6.0, "w_400/Bamboo_Leaf_Dongdongju_w400.png", 0.0, MAKGEOLLI),

                // 양주
                Drink.create("이엔제이 브랜디", "E&J Brandy", 40.0, "w_400/E&J_Brandy_w400.png", 0.0, YANGJU),
                Drink.create("로얄 살루트 21 스카치 위스키", "Royal Salute 21 Year Old Blended Scotch Whisky", 40.0, "w_400/Royal_Salute_21_Year_Old_Blended_Scotch_Whisky_w400.png", 0.0, YANGJU),
                Drink.create("발렌타인 12년산 베리 올드 블렌디드 스카치 위스키", "Ballantine's very old Scotch Whisky 21 Years", 43.0, "w_400/Ballantine's_very_old_Scotch_Whisky_21_Years_w400.png", 0.0, YANGJU),
                Drink.create("스카치 블루 21년", "Scotch Blue 21years old", 40.0, "w_400/Scotch_Blue_21years_old_w400.png", 0.0, YANGJU),
                Drink.create("까뮤XO 엘레강스", "Camus XO ELECANCE", 40.0, "w_400/Camus_XO_ELECANCE_w400.png", 0.0, YANGJU),
                Drink.create("듀어스 18년산 블랜디드 스카치 위스키", "Dewar's 18 Blended Scotch Whisky", 40.0, "w_400/Dewar's_18_Blended_Scotch_Whisky_w400.png", 0.0, YANGJU),
                Drink.create("글렌피딕 21년 리저바 럼 캐스크 피니쉬", "Glenfiddich 21years old Reserva Rum Cask Finish", 40.0, "w_400/Glenfiddich_21years_old_Reserva_Rum_Cask_Finish_w400.png", 0.0, YANGJU),
                Drink.create("조니 워커 블랙 라벨 12년 산", "Johnnie Walker Black Label 12 Year Old", 40.0, "w_400/Johnnie_Walker_Black_Label_12_Year_Old_w400.png", 0.0, YANGJU),

                // 칵테일
                Drink.create("그레이하운드", "Greyhound", 12.0, "w_400/greyhound_w400.png", 0.0, COCKTAIL),
                Drink.create("올드 패션드", "Old Fashioned", 32.0, "w_400/oldfashioned_w400.png", 0.0, COCKTAIL),
                Drink.create("마가리타", "Margarita", 23.0, "w_400/margarita_w400.png", 0.0, COCKTAIL),
                Drink.create("블루 라군", "Blue Lagoon", 0.0, "w_400/bluelagoon_w400.png", 0.0, COCKTAIL),
                Drink.create("솔티 독", "Salty Dog", 0.0, "w_400/saltydog_w400.png", 0.0, COCKTAIL),
                Drink.create("섹스 온 더 비치", "Sex on the beach", 0.0, "w_400/sexonthebeach_w400.png", 0.0, COCKTAIL),
                Drink.create("캘리포니아 레모네이드", "California Lemonade", 0.0, "w_400/calfornialemonade_w400.png", 0.0, COCKTAIL),
                Drink.create("밥 말리", "Bob Marley", 0.0, "w_400/bobmarley_w400.png", 0.0, COCKTAIL),
                Drink.create("브랜디 플립", "Brandy Flip", 0.0, "w_400/brandyflip_w400.png", 0.0, COCKTAIL),
                Drink.create("샹그리아", "Sangria", 0.0, "w_400/sangria_w400.png", 0.0, COCKTAIL),
                Drink.create("마티니", "Martini", 0.0, "w_400/martini_w400.png", 0.0, COCKTAIL),

                // 기타
                Drink.create("심술", "grumpy", 7.0, "w_400/grumpy_w400.png", 0.0, ETC),
                Drink.create("써머스비", "SomersBy", 4.5, "w_400/SomersBy_w400.png", 0.0, ETC),
                Drink.create("템트 세븐", "Tempt 7", 4.5, "w_400/Tempt_7_w400.png", 0.0, ETC),
                Drink.create("템트 9", "Tempt 9", 4.5, "w_400/Tempt_9_w400.png", 0.0, ETC),
                Drink.create("복숭아 소다", "Peach Soda", 3.0, "w_400/Peach_Soda_w400.png", 0.0, ETC),
                Drink.create("이슬 톡톡", "Jinro TokTok", 3.0, "w_400/Jinro_TokTok_w400.png", 0.0, ETC),
                Drink.create("좋은데이 민트초코", "jot-eun-day-mint-choco", 12.5, "w_400/jot-eun-day-mint-choco_w400.png", 0.0, ETC)

        );

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);
        drinkRepository.saveAll(drinks_spread);

        // Member Data
        Provider provider = Provider.of("1234", ProviderName.TEST);
        Member member = Member.createAnonymousMember();

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
