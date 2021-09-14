package com.jujeol;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
public class DataLoader implements CommandLineRunner {

    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(
            DrinkRepository drinkRepository,
            CategoryRepository categoryRepository
    ) {
        this.drinkRepository = drinkRepository;
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
        Drink stella = Drink.create("스텔라 아르투아", "STELLA ARTOIS ", 5.0, createFilePath("stella_artois"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink kgb = Drink.create("KGB 레몬", "KGB Lemon", 5.0,  createFilePath("kgb"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink efes = Drink.create("에페스", "EFES",5.0,  createFilePath("efes"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink tiger_rad = Drink.create("타이거 라들러 자몽", "Tiger Rad", 2.0,  createFilePath("tiger_raddler_grapefruit"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink tsingtao = Drink.create("칭따오 스타우트", "TSINGTAO STOUT", 4.8,  createFilePath("tsingtao_w400"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink gom_pyo = Drink.create("세븐브로이 곰표 밀맥주", "7brau Gompyo wheatbear", 4.5,  createFilePath("gom_pyo"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink ob = Drink.create("오비 라거", "OB Lager", 4.6, createFilePath("ob_lager"), 0.0, BEER, "이 음료는 맥주이다.");
        Drink tigerLemon = Drink.create("타이거 라들러 레몬", "Tiger Lemon", 2.0,  createFilePath("tiger_raddler_lemon"), 0.0, BEER, "이 음료는 맥주이다.");

        // 스프리드 시트 데이터 추가
        final List<Drink> drinks_spread = Arrays.asList(
                // 맥주
                Drink.create("쥬시후레쉬맥주", "Juicy Fresh Beer", 4.5, createFilePath("Juicy_Fresh_Beer"), 0.0, BEER, "이 음료는 맥주이다."),
                Drink.create("금성맥주", "Gold Star Beer", 5.1, createFilePath("Gold_Star_Beer"), 0.0, BEER, "이 음료는 맥주이다."),
                Drink.create("제주 거멍 에일", "Jeju Geomeong Ale", 4.3, createFilePath("Jeju_Geomeong_Ale"), 0.0, BEER, "이 음료는 맥주이다."),
                Drink.create("에페스 필스너", "EFES Pilsner", 5.0, createFilePath("EFES_Pilsner"), 0.0, BEER, "이 음료는 맥주이다."),
                Drink.create("오드 괴즈 틸퀸 아렌시아", "Oude Gueze Tilquin a L'Ancienne", 7.0, createFilePath("Oude_Gueze_Tilquin_a_L'Ancienne"), 0.0, BEER, "이 음료는 맥주이다."),

                // 소주
                Drink.create("시원한청풍소주", "C1Soju", 17.2, createFilePath("c1soju"), 0.0, SOJU, "이 술은 소주이다."),
                Drink.create("참이슬", "Chamisul Soju", 16.9, createFilePath("charm_i_sel_soju"), 0.0, SOJU, "이 술은 소주이다."),
                Drink.create("처음처럼", "cheo-um-cheo-rum Soju", 16.5, createFilePath("cheo_um_cheo_rum_soju"), 0.0, SOJU, "이 술은 소주이다."),
                Drink.create("자몽에이슬", "jamong-eh-i-sul-soju", 13.0, createFilePath("jamong_eh_i_sul_soju"), 0.0, SOJU, "이 술은 소주이다."),
                Drink.create("참이슬 오리지날", "charm-i-sel-soju", 20.1, createFilePath("charm_i_sel_original_soju"), 0.0, SOJU, "이 술은 소주이다."),
                Drink.create("진로", "jinro-soju", 16.5, createFilePath("jinro_soju"), 0.0, SOJU, "이 술은 소주이다."),

                // 와인
                Drink.create("탈로 프리미티보 디 만두리아", "Talo Primitivo Di Manduria", 14.0, createFilePath("Talo_Primitivo_Di_Manduria"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("킴 크로포드, 피노 그리", "Kim Crawford, Pinot Gris", 13.0, createFilePath("Kim_Crawford_Pinot_Gris"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("커클랜드 시그니처 토니 포트 10년", "Kirkland Signature Tawny Port 10 years old", 20.0, createFilePath("Kirkland_Signature_Tawny_Port_10_years_old"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("버니니 클래식", "Bernini Classic", 4.5, createFilePath("Bernini_Classic"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("T7 까베르네 소비뇽", "T7 Cabernet Sauvignon", 13.0, createFilePath("T7_Cabernet_Sauvignon"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("복숭아 와인", "Peach Wine", 12.0, createFilePath("Peach_Wine"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("그란 띠에라 비노 블랑코", "Gran Tierra vino blanco", 10.5, createFilePath("Gran_Tierra_vino_blanco"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("비냐 란자 틴토", "VINA LANZAR Tinto", 11.5, createFilePath("VINA_LANZAR_Tinto"), 0.0, WINE, "이 술은 와인이다."),
                Drink.create("빈야드 샤도네이", "Vineyards Chardonnay", 13.0, createFilePath("Vineyards_Chardonnay"), 0.0, WINE, "이 술은 와인이다."),

                // 막걸리
                Drink.create("느린마을 막걸리", "Slow City Makgeolli", 6.0, createFilePath("slowbrew_makgeulli"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("과천미주", "Gwacheon Mijoo", 9.0, createFilePath("Gwacheon_Mijoo"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("관악산 생막걸리", "Gwanak Mountain Makgeolli", 6.0, createFilePath("Gwanak_Mountain_Makgeolli"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("국순당생막걸리", "Guksundang Makgeolli", 6.0, createFilePath("Guksundang_Makgeolli"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("경주법주 쌀막걸리", "GyeongjuBeopju Rice Makgeolli", 6.0, createFilePath("Gyeongju_Beopju_Rice_Makgeolli"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("소백산 막걸리", "Sobaeksan Makgeolli", 6.0, createFilePath("Sobaeksan_Makgeolli"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),
                Drink.create("대잎 동동주", "Bamboo Leaf Dongdongju", 6.0, createFilePath("Bamboo_Leaf_Dongdongju"), 0.0, MAKGEOLLI, "이 술은 막걸리다."),

                // 양주
                Drink.create("이엔제이 브랜디", "E&J Brandy", 40.0, createFilePath("E&J_Brandy"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("로얄 살루트 21 스카치 위스키", "Royal Salute 21 Year Old Blended Scotch Whisky", 40.0, createFilePath("Royal_Salute_21_Year_Old_Blended_Scotch_Whisky"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("발렌타인 12년산 베리 올드 블렌디드 스카치 위스키", "Ballantine's very old Scotch Whisky 21 Years", 43.0, createFilePath("Ballantine's_very_old_Scotch_Whisky_21_Years"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("스카치 블루 21년", "Scotch Blue 21years old", 40.0, createFilePath("Scotch_Blue_21years_old"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("까뮤XO 엘레강스", "Camus XO ELECANCE", 40.0, createFilePath("Camus_XO_ELECANCE"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("듀어스 18년산 블랜디드 스카치 위스키", "Dewar's 18 Blended Scotch Whisky", 40.0, createFilePath("Dewar's_18_Blended_Scotch_Whisky"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("글렌피딕 21년 리저바 럼 캐스크 피니쉬", "Glenfiddich 21years old Reserva Rum Cask Finish", 40.0, createFilePath("Glenfiddich_21years_old_Reserva_Rum_Cask_Finish"), 0.0, YANGJU, "이 술은 양주이다."),
                Drink.create("조니 워커 블랙 라벨 12년 산", "Johnnie Walker Black Label 12 Year Old", 40.0, createFilePath("Johnnie_Walker_Black_Label_12_Year_Old"), 0.0, YANGJU, "이 술은 양주이다."),

                // 칵테일
                Drink.create("그레이하운드", "Greyhound", 12.0, createFilePath("greyhound"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("올드 패션드", "Old Fashioned", 32.0, createFilePath("oldfashioned"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("마가리타", "Margarita", 23.0, createFilePath("margarita"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("블루 라군", "Blue Lagoon", 0.0, createFilePath("bluelagoon"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("솔티 독", "Salty Dog", 0.0, createFilePath("saltydog"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("섹스 온 더 비치", "Sex on the beach", 0.0, createFilePath("sexonthebeach"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("캘리포니아 레모네이드", "California Lemonade", 0.0, createFilePath("calfornialemonade"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("밥 말리", "Bob Marley", 0.0, createFilePath("bobmarley"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("브랜디 플립", "Brandy Flip", 0.0, createFilePath("brandyflip"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("샹그리아", "Sangria", 0.0, createFilePath("sangria"), 0.0, COCKTAIL, "이 술은 칵테일이다."),
                Drink.create("마티니", "Martini", 0.0, createFilePath("martini"), 0.0, COCKTAIL, "이 술은 칵테일이다."),

                // 기타
                Drink.create("심술", "grumpy", 7.0, createFilePath("simsul"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("써머스비", "SomersBy", 4.5, createFilePath("SomersBy"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("템트 세븐", "Tempt 7", 4.5, createFilePath("Tempt_7"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("템트 9", "Tempt 9", 4.5, createFilePath("Tempt_9"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("복숭아 소다", "Peach Soda", 3.0, createFilePath("Peach_Soda"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("이슬 톡톡", "Jinro TokTok", 3.0, createFilePath("Jinro_Tok_Tok"), 0.0, ETC, "이 술은 기타 술이다."),
                Drink.create("좋은데이 민트초코", "jot-eun-day-mint-choco", 12.5,createFilePath("jo_eun_day_mint_choco"), 0.0, ETC, "이 술은 기타 술이다.")

        );

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);
        drinkRepository.saveAll(drinks_spread);
    }

    private List<String> createFilePath(String drinkName) {
        String smallFilePath = "https://dmaxaug2ve9od.cloudfront.net/w_200/" + drinkName + "_w200.png";
        String mediumFilePath = "https://dmaxaug2ve9od.cloudfront.net/w_400/" + drinkName + "_w400.png";
        String largeFilePath = "https://dmaxaug2ve9od.cloudfront.net/w_600/" + drinkName + "_w600.png";

        return List.of(smallFilePath, mediumFilePath, largeFilePath);
    }
}