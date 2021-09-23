package com.jujeol;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.category.domain.CategoryRepository;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.drink.drink.domain.ImageFilePath;
import com.jujeol.drink.drink.domain.repository.DrinkRepository;
import com.jujeol.member.auth.domain.Provider;
import com.jujeol.member.auth.domain.ProviderName;
import com.jujeol.member.auth.util.JwtTokenProvider;
import com.jujeol.member.member.application.dto.PreferenceDto;
import com.jujeol.member.member.domain.Biography;
import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.nickname.Nickname;
import com.jujeol.member.member.domain.repository.MemberRepository;
import com.jujeol.preference.application.PreferenceService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PreferenceService preferenceService;

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
        Drink stella = Drink
                .create("스텔라 아르투아", "STELLA ARTOIS ", 5.0, createFilePath("stella_artois"), 0.0,
                        BEER, "다크 에일. 포터. 특유의 탄내, 커피향, 초콜릿향이 난다. \n"
                        + "거멍이라는 뜻은 검다의 제주도의 방언이다.\n"
                        + "흑맥주 특유의매력은 살리면서 대중성까지 겸비한 제품이다.");
        Drink kgb = Drink.create("KGB 레몬", "KGB Lemon", 5.0, createFilePath("kgb"), 0.0, BEER,
                "다크 에일. 포터. 특유의 탄내, 커피향, 초콜릿향이 난다. \n"
                    + "거멍이라는 뜻은 검다의 제주도의 방언이다.\n"
                    + "흑맥주 특유의매력은 살리면서 대중성까지 겸비한 제품이다.");
        Drink efes = Drink
                .create("에페스", "EFES", 5.0, createFilePath("efes"), 0.0, BEER, "다크 에일. 포터. 특유의 탄내, 커피향, 초콜릿향이 난다. \n"
                    + "거멍이라는 뜻은 검다의 제주도의 방언이다.\n"
                    + "흑맥주 특유의매력은 살리면서 대중성까지 겸비한 제품이다.");
        Drink tiger_rad = Drink
                .create("타이거 라들러 자몽", "Tiger Rad", 2.0, createFilePath("tiger_raddler_grapefruit"),
                        0.0, BEER, "도수는 16.5도다. 시리즈로 20도의 진한처럼과 16.0도의 처음처럼 순한도 있다.\n"
                        + "일본에서는 산토리에서 수입하여 원래 이름인 경월GREEN으로 발매 중이며 도수는 25도와 20도다.\n"
                        + "첫 출시 당시 최소한의 선이라 불리던 알콜 도수 20도를 깨고(처음에는 20도로 출시됐다.) 그 당기 금기로 여겨졌던 19.8도를 시도했다. 그런 연유로 참이슬 한병으로 취할 것을 처음처럼은 한병 반~두병을 마셔야 취한다고 할 정도로 순하다고들 이야기되고 있다. 애당초 0.2도 정도로는 그다지 큰 차이가 나지 않기 때문에 90% 이상은 기분 탓이지만 말이다.");
        Drink tsingtao = Drink
                .create("칭따오 스타우트", "TSINGTAO STOUT", 4.8, createFilePath("tsingtao_w400"), 0.0,
                        BEER, "안녕하재오 저는 프론트엔드에서 에러핸들링 발표를 맡게 된 티케입니다.");
        Drink gom_pyo = Drink
                .create("세븐브로이 곰표 밀맥주", "7brau Gompyo wheatbear", 4.5, createFilePath("gom_pyo"),
                        0.0, BEER, "엘리자베스 2세 여왕의 대관식을 기념하기 위해 생산된 스카치 위스키다.\n"
                        + "시바스 리갈로 유명한 시바스 브라더스에서 보유한 원액 중 가장 귀중한 원액만을 모아 21년간 숙성하여 만들었다.\n"
                        + "스모키한 맛과, 독한 꿀 맛 같은 스위트한 맛, 도수에 비해 잘 넘어가는 목넘김이 특징이다.");
        Drink ob = Drink.create("오비 라거", "OB Lager", 4.6, createFilePath("ob_lager"), 0.0, BEER,
                "엘리자베스 2세 여왕의 대관식을 기념하기 위해 생산된 스카치 위스키다.\n"
                    + "시바스 리갈로 유명한 시바스 브라더스에서 보유한 원액 중 가장 귀중한 원액만을 모아 21년간 숙성하여 만들었다.\n"
                    + "스모키한 맛과, 독한 꿀 맛 같은 스위트한 맛, 도수에 비해 잘 넘어가는 목넘김이 특징이다.");
        Drink tigerLemon = Drink
                .create("타이거 라들러 레몬", "Tiger Lemon", 2.0, createFilePath("tiger_raddler_lemon"),
                        0.0, BEER, "엘리자베스 2세 여왕의 대관식을 기념하기 위해 생산된 스카치 위스키다.\n"
                        + "시바스 리갈로 유명한 시바스 브라더스에서 보유한 원액 중 가장 귀중한 원액만을 모아 21년간 숙성하여 만들었다.\n"
                        + "스모키한 맛과, 독한 꿀 맛 같은 스위트한 맛, 도수에 비해 잘 넘어가는 목넘김이 특징이다.");

        // 스프리드 시트 데이터 추가
        final List<Drink> drinks_spread = Arrays.asList(
                // 맥주
                Drink.create("쥬시후레쉬맥주", "Juicy Fresh Beer", 4.5, createFilePath("Juicy_Fresh_Beer"),
                        0.0, BEER, "발렌타인은 탁하지 않은 호박빛으로 농축된 농후함과 잘 블렌드 된 위스키들의 혼합을 보여준다.\n"
                        + "발렌타인 21년산의 가격은 12년산의 가격에 4~5배를 뛰어넘는다.\n"
                        + "40도가 넘는 술임에도 목넘김이 부드럽다."),
                Drink.create("금성맥주", "Gold Star Beer", 5.1, createFilePath("Gold_Star_Beer"), 0.0,
                        BEER, "발렌타인은 탁하지 않은 호박빛으로 농축된 농후함과 잘 블렌드 된 위스키들의 혼합을 보여준다.\n"
                        + "발렌타인 21년산의 가격은 12년산의 가격에 4~5배를 뛰어넘는다.\n"
                        + "40도가 넘는 술임에도 목넘김이 부드럽다."),
                Drink.create("제주 거멍 에일", "Jeju Geomeong Ale", 4.3,
                        createFilePath("Jeju_Geomeong_Ale"), 0.0, BEER, "발렌타인은 탁하지 않은 호박빛으로 농축된 농후함과 잘 블렌드 된 위스키들의 혼합을 보여준다.\n"
                        + "발렌타인 21년산의 가격은 12년산의 가격에 4~5배를 뛰어넘는다.\n"
                        + "40도가 넘는 술임에도 목넘김이 부드럽다."),
                Drink.create("에페스 필스너", "EFES Pilsner", 5.0, createFilePath("EFES_Pilsner"), 0.0,
                        BEER, "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),
                Drink.create("오드 괴즈 틸퀸 아렌시아", "Oude Gueze Tilquin a L'Ancienne", 7.0,
                        createFilePath("Oude_Gueze_Tilquin_a_L'Ancienne"), 0.0, BEER,
                        "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),

                // 소주
                Drink.create("시원한청풍소주", "C1Soju", 17.2, createFilePath("c1soju"), 0.0, SOJU,
                        "호랑이 생막걸리는 생 발효를 통해 맛을 낸 웰빙형 막걸리입니다. 시각적으로 강한 호랑이의 느낌과는 반대로 부드러운 바디감을 가지고 있으며, 맛과 향이 풍부하고 목 넘김이 뛰어납니다."),
                Drink.create("참이슬", "Chamisul Soju", 16.9, createFilePath("charm_i_sel_soju"), 0.0,
                        SOJU, "호랑이 생막걸리는 생 발효를 통해 맛을 낸 웰빙형 막걸리입니다. 시각적으로 강한 호랑이의 느낌과는 반대로 부드러운 바디감을 가지고 있으며, 맛과 향이 풍부하고 목 넘김이 뛰어납니다."),
                Drink.create("처음처럼", "cheo-um-cheo-rum Soju", 16.5,
                        createFilePath("cheo_um_cheo_rum_soju"), 0.0, SOJU, "호랑이 생막걸리는 생 발효를 통해 맛을 낸 웰빙형 막걸리입니다. 시각적으로 강한 호랑이의 느낌과는 반대로 부드러운 바디감을 가지고 있으며, 맛과 향이 풍부하고 목 넘김이 뛰어납니다."),
                Drink.create("자몽에이슬", "jamong-eh-i-sul-soju", 13.0,
                        createFilePath("jamong_eh_i_sul_soju"), 0.0, SOJU, "산사나무 열매로 만든 술입니다. 산사나무 열매는 체했을때와 섬장 건강과 혈액순환 그리고 노화방지를 하는데 탁월한 효능을 가지고 있기에 한약재료로도 많이 사용됩니다. 새콤달콤하고 달면서 부드러운 목 넘김이 일품인 제품입니다."),
                Drink.create("참이슬 오리지날", "charm-i-sel-soju", 20.1,
                        createFilePath("charm_i_sel_original_soju"), 0.0, SOJU, "천매, 매실원주, 원매 프리미엄, 원매 등 매실주를 전문으로 생산하고 있는 '더한주류'에서 2018년 새롭게 출시한 황매실을 원료로 증류주이다. 국내의 다른 매실주는 5~6월경 수확되는 청매를 이용하는 반면,"
                        + " 매실원주는 보다 완숙한 상태에서 7월경 수확되는 황매를 이용한다. 황매는 청매에 비해 향미가 뛰어나지만 고가인 단점이 있으나 최상의 원료로"
                        + " 최상의 제품을 만든다는 제품 철학으로 황매를 이용한다. '서울의 밤'은 스트레이트로 맛을 느끼고, 언더락을 풍부한 향을 느끼고, 칵테일로 완벽한 조화를 경험할 수 있는 새로운 차원의 매실 증류주이다."),
                Drink.create("진로", "jinro-soju", 16.5, createFilePath("jinro_soju"), 0.0, SOJU,
                        "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),

                // 와인
                Drink.create("탈로 프리미티보 디 만두리아", "Talo Primitivo Di Manduria", 14.0,
                        createFilePath("Talo_Primitivo_Di_Manduria"), 0.0, WINE, "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),
                Drink.create("킴 크로포드, 피노 그리", "Kim Crawford, Pinot Gris", 13.0,
                        createFilePath("Kim_Crawford_Pinot_Gris"), 0.0, WINE, "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),
                Drink.create("커클랜드 시그니처 토니 포트 10년", "Kirkland Signature Tawny Port 10 years old",
                        20.0, createFilePath("Kirkland_Signature_Tawny_Port_10_years_old"), 0.0,
                        WINE, "도수는 21도, 23도, 25도 세가지가 있습니다. 100% 한국 쌀을 35% 도정하여 저온 숙성시킨 프리미엄 증류식 소주입니다. 곡물의 향이 은은하게 퍼져 나오는 것이 일품인 제품입니다."),
                Drink.create("버니니 클래식", "Bernini Classic", 4.5, createFilePath("Bernini_Classic"),
                        0.0, WINE, "배 생쌀을 그대로 갈아 발효시키는 \"생쌀발효법\"으로 빚어 뒤끝이 없는 한국의 전통 약주입니다. 좋은쌀과 좋은 누룩으로 빚은 백세주는 구기자, 오미자, 홍삼 등 12가지 한약재를 넣어 함께 발효시킨 술입니다."),
                Drink.create("T7 까베르네 소비뇽", "T7 Cabernet Sauvignon", 13.0,
                        createFilePath("T7_Cabernet_Sauvignon"), 0.0, WINE, "배 생쌀을 그대로 갈아 발효시키는 \"생쌀발효법\"으로 빚어 뒤끝이 없는 한국의 전통 약주입니다. 좋은쌀과 좋은 누룩으로 빚은 백세주는 구기자, 오미자, 홍삼 등 12가지 한약재를 넣어 함께 발효시킨 술입니다."),
                Drink.create("복숭아 와인", "Peach Wine", 12.0, createFilePath("Peach_Wine"), 0.0, WINE,
                        "이 술은 와인이다."),
                Drink.create("그란 띠에라 비노 블랑코", "Gran Tierra vino blanco", 10.5,
                        createFilePath("Gran_Tierra_vino_blanco"), 0.0, WINE, "코젤은 염소라는 뜻이다.\n"
                        + "매우 순한 흑맥주. 커피향이 살짝 풍기고 탄산은 적은 편. 쓴맛을 즐기지 않는 사람에게 추천한다.\n"
                        + "시나몬가루와 설탕을 잔 입구에 바르면 더 맛있게 먹을 수 있다."),
                Drink.create("비냐 란자 틴토", "VINA LANZAR Tinto", 11.5,
                        createFilePath("VINA_LANZAR_Tinto"), 0.0, WINE, "코젤은 염소라는 뜻이다.\n"
                        + "매우 순한 흑맥주. 커피향이 살짝 풍기고 탄산은 적은 편. 쓴맛을 즐기지 않는 사람에게 추천한다.\n"
                        + "시나몬가루와 설탕을 잔 입구에 바르면 더 맛있게 먹을 수 있다."),
                Drink.create("빈야드 샤도네이", "Vineyards Chardonnay", 13.0,
                        createFilePath("Vineyards_Chardonnay"), 0.0, WINE, "코젤은 염소라는 뜻이다.\n"
                        + "매우 순한 흑맥주. 커피향이 살짝 풍기고 탄산은 적은 편. 쓴맛을 즐기지 않는 사람에게 추천한다.\n"
                        + "시나몬가루와 설탕을 잔 입구에 바르면 더 맛있게 먹을 수 있다."),

                // 막걸리
                Drink.create("느린마을 막걸리", "Slow City Makgeolli", 6.0,
                        createFilePath("slowbrew_makgeulli"), 0.0, MAKGEOLLI, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("과천미주", "Gwacheon Mijoo", 9.0, createFilePath("Gwacheon_Mijoo"), 0.0,
                        MAKGEOLLI, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("관악산 생막걸리", "Gwanak Mountain Makgeolli", 6.0,
                        createFilePath("Gwanak_Mountain_Makgeolli"), 0.0, MAKGEOLLI, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("국순당생막걸리", "Guksundang Makgeolli", 6.0,
                        createFilePath("Guksundang_Makgeolli"), 0.0, MAKGEOLLI, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("경주법주 쌀막걸리", "GyeongjuBeopju Rice Makgeolli", 6.0,
                        createFilePath("Gyeongju_Beopju_Rice_Makgeolli"), 0.0, MAKGEOLLI,
                        "진은 곡물을 발효시킨 것에 향신료를 넣고 증류한 백색 증류주이다.\n"
                            + "도수가 높으므로 스트레이트로 마시지 않는 것이 좋다."),
                Drink.create("소백산 막걸리", "Sobaeksan Makgeolli", 6.0,
                        createFilePath("Sobaeksan_Makgeolli"), 0.0, MAKGEOLLI, "진은 곡물을 발효시킨 것에 향신료를 넣고 증류한 백색 증류주이다.\n"
                        + "도수가 높으므로 스트레이트로 마시지 않는 것이 좋다."),
                Drink.create("대잎 동동주", "Bamboo Leaf Dongdongju", 6.0,
                        createFilePath("Bamboo_Leaf_Dongdongju"), 0.0, MAKGEOLLI, "진은 곡물을 발효시킨 것에 향신료를 넣고 증류한 백색 증류주이다.\n"
                        + "도수가 높으므로 스트레이트로 마시지 않는 것이 좋다."),

                // 양주
                Drink.create("이엔제이 브랜디", "E&J Brandy", 40.0, createFilePath("E&J_Brandy"), 0.0,
                        YANGJU, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("로얄 살루트 21 스카치 위스키", "Royal Salute 21 Year Old Blended Scotch Whisky",
                        40.0, createFilePath("Royal_Salute_21_Year_Old_Blended_Scotch_Whisky"), 0.0,
                        YANGJU, "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                        + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                        + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("발렌타인 12년산 베리 올드 블렌디드 스카치 위스키",
                        "Ballantine's very old Scotch Whisky 21 Years", 43.0,
                        createFilePath("Ballantine's_very_old_Scotch_Whisky_21_Years"), 0.0, YANGJU,
                        "색은 밝고 희끄무레하다. 다른 밀맥들이 탁하고 뿌옇게 진한 색을 띠는 것과는 대조된다.\n"
                            + "제대로 즐기기 위해서는 호가든 전용잔에 따라먹는것을 추천하는데, 우선 잔의 2/3 지점에 있는 선까지 호가든을 따르고 남은 1/3부분엔 거품을 충분히 내어 따라야 그 맛을 제대로 즐길 수 있다.\n"
                            + "이렇게 하는 이유는 남은 효모를 따라내기 위한 것. 병을 많이 흔들어 거품을 많이 내어 따를수록 오렌지 향이 진하게 난다."),
                Drink.create("스카치 블루 21년", "Scotch Blue 21years old", 40.0,
                        createFilePath("Scotch_Blue_21years_old"), 0.0, YANGJU, "칠면조 사냥을 하면서 즐겼다고 해서 칠면조가 그려져 있다는 속설이 있다.\n"
                        + "알콜의 톡 쏘는 향은 없는 편이고 과자 누네띠네와 같은 단향이 올라온다.\n"
                        + "입안 전체로 신선한 꿀 같은 달큰함이 가득 채워지며 알싸한 맛도 살짝 느껴진다. \n"
                        + "술의 질감은 밀크 초콜릿을 녹여 먹었을 때 거의 끝물쯤에 입천장과 혓바닥에 남아있는 초콜릿의 몽글몽글한 질감이 느껴진다."),
                Drink.create("까뮤XO 엘레강스", "Camus XO ELECANCE", 40.0,
                        createFilePath("Camus_XO_ELECANCE"), 0.0, YANGJU, "칠면조 사냥을 하면서 즐겼다고 해서 칠면조가 그려져 있다는 속설이 있다.\n"
                        + "알콜의 톡 쏘는 향은 없는 편이고 과자 누네띠네와 같은 단향이 올라온다.\n"
                        + "입안 전체로 신선한 꿀 같은 달큰함이 가득 채워지며 알싸한 맛도 살짝 느껴진다. \n"
                        + "술의 질감은 밀크 초콜릿을 녹여 먹었을 때 거의 끝물쯤에 입천장과 혓바닥에 남아있는 초콜릿의 몽글몽글한 질감이 느껴진다."),
                Drink.create("듀어스 18년산 블랜디드 스카치 위스키", "Dewar's 18 Blended Scotch Whisky", 40.0,
                        createFilePath("Dewar's_18_Blended_Scotch_Whisky"), 0.0, YANGJU,
                        "칠면조 사냥을 하면서 즐겼다고 해서 칠면조가 그려져 있다는 속설이 있다.\n"
                            + "알콜의 톡 쏘는 향은 없는 편이고 과자 누네띠네와 같은 단향이 올라온다.\n"
                            + "입안 전체로 신선한 꿀 같은 달큰함이 가득 채워지며 알싸한 맛도 살짝 느껴진다. \n"
                            + "술의 질감은 밀크 초콜릿을 녹여 먹었을 때 거의 끝물쯤에 입천장과 혓바닥에 남아있는 초콜릿의 몽글몽글한 질감이 느껴진다."),
                Drink.create("글렌피딕 21년 리저바 럼 캐스크 피니쉬",
                        "Glenfiddich 21years old Reserva Rum Cask Finish", 40.0,
                        createFilePath("Glenfiddich_21years_old_Reserva_Rum_Cask_Finish"), 0.0,
                        YANGJU, "칠면조 사냥을 하면서 즐겼다고 해서 칠면조가 그려져 있다는 속설이 있다.\n"
                        + "알콜의 톡 쏘는 향은 없는 편이고 과자 누네띠네와 같은 단향이 올라온다.\n"
                        + "입안 전체로 신선한 꿀 같은 달큰함이 가득 채워지며 알싸한 맛도 살짝 느껴진다. \n"
                        + "술의 질감은 밀크 초콜릿을 녹여 먹었을 때 거의 끝물쯤에 입천장과 혓바닥에 남아있는 초콜릿의 몽글몽글한 질감이 느껴진다."),
                Drink.create("조니 워커 블랙 라벨 12년 산", "Johnnie Walker Black Label 12 Year Old", 40.0,
                        createFilePath("Johnnie_Walker_Black_Label_12_Year_Old"), 0.0, YANGJU,
                        "칠면조 사냥을 하면서 즐겼다고 해서 칠면조가 그려져 있다는 속설이 있다.\n"
                            + "알콜의 톡 쏘는 향은 없는 편이고 과자 누네띠네와 같은 단향이 올라온다.\n"
                            + "입안 전체로 신선한 꿀 같은 달큰함이 가득 채워지며 알싸한 맛도 살짝 느껴진다. \n"
                            + "술의 질감은 밀크 초콜릿을 녹여 먹었을 때 거의 끝물쯤에 입천장과 혓바닥에 남아있는 초콜릿의 몽글몽글한 질감이 느껴진다."),

                // 칵테일
                Drink.create("그레이하운드", "Greyhound", 12.0, createFilePath("greyhound"), 0.0,
                        COCKTAIL, "약한 단맛과 신맛이 맴돈다.\n"
                        + "목넘김이 부드러운 편인다."),
                Drink.create("올드 패션드", "Old Fashioned", 32.0, createFilePath("oldfashioned"), 0.0,
                        COCKTAIL, "약한 단맛과 신맛이 맴돈다.\n"
                        + "목넘김이 부드러운 편인다."),
                Drink.create("마가리타", "Margarita", 23.0, createFilePath("margarita"), 0.0, COCKTAIL,
                        "약한 단맛과 신맛이 맴돈다.\n"
                            + "목넘김이 부드러운 편인다."),
                Drink.create("블루 라군", "Blue Lagoon", 0.0, createFilePath("bluelagoon"), 0.0,
                        COCKTAIL, "약한 단맛과 신맛이 맴돈다.\n"
                        + "목넘김이 부드러운 편인다."),
                Drink.create("솔티 독", "Salty Dog", 0.0, createFilePath("saltydog"), 0.0, COCKTAIL,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("섹스 온 더 비치", "Sex on the beach", 0.0, createFilePath("sexonthebeach"),
                        0.0, COCKTAIL, "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                        + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("캘리포니아 레모네이드", "California Lemonade", 0.0,
                        createFilePath("calfornialemonade"), 0.0, COCKTAIL, "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                        + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("밥 말리", "Bob Marley", 0.0, createFilePath("bobmarley"), 0.0, COCKTAIL,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("브랜디 플립", "Brandy Flip", 0.0, createFilePath("brandyflip"), 0.0,
                        COCKTAIL, "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                        + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("샹그리아", "Sangria", 0.0, createFilePath("sangria"), 0.0, COCKTAIL,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("마티니", "Martini", 0.0, createFilePath("martini"), 0.0, COCKTAIL,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),

                // 기타
                Drink.create("심술", "grumpy", 7.0, createFilePath("simsul"), 0.0, ETC,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("써머스비", "SomersBy", 4.5, createFilePath("SomersBy"), 0.0, ETC,
                        "골드 럼+블루 큐라소(또는 미도리), 그레나딘 시럽, 바나나 리큐어(또는 오렌지 리큐어)\n"
                            + "초록, 파랑, 노랑 세 가지 색으로 만든 칵테일"),
                Drink.create("템트 세븐", "Tempt 7", 4.5, createFilePath("Tempt_7"), 0.0, ETC,
                        "하쯔루는 엄선된 쌀과 정미기술 그리고 롯코산의 깨끗한 물을 이용하여 청아하고 맛의 균형이 잡힌 다이긴죠를 생산해 냅니다. 신맛과 단맛의 균형이 잘 잡힌 술로 목넘김이 부드럽고 담백하고 마일드한 맛이 일품입니다."),
                Drink.create("템트 9", "Tempt 9", 4.5, createFilePath("Tempt_9"), 0.0, ETC,
                        "하쯔루는 엄선된 쌀과 정미기술 그리고 롯코산의 깨끗한 물을 이용하여 청아하고 맛의 균형이 잡힌 다이긴죠를 생산해 냅니다. 신맛과 단맛의 균형이 잘 잡힌 술로 목넘김이 부드럽고 담백하고 마일드한 맛이 일품입니다."),
                Drink.create("복숭아 소다", "Peach Soda", 3.0, createFilePath("Peach_Soda"), 0.0, ETC,
                        "하쯔루는 엄선된 쌀과 정미기술 그리고 롯코산의 깨끗한 물을 이용하여 청아하고 맛의 균형이 잡힌 다이긴죠를 생산해 냅니다. 신맛과 단맛의 균형이 잘 잡힌 술로 목넘김이 부드럽고 담백하고 마일드한 맛이 일품입니다."),
                Drink.create("이슬 톡톡", "Jinro TokTok", 3.0, createFilePath("Jinro_Tok_Tok"), 0.0,
                        ETC, "하쯔루는 엄선된 쌀과 정미기술 그리고 롯코산의 깨끗한 물을 이용하여 청아하고 맛의 균형이 잡힌 다이긴죠를 생산해 냅니다. 신맛과 단맛의 균형이 잘 잡힌 술로 목넘김이 부드럽고 담백하고 마일드한 맛이 일품입니다."),
                Drink.create("좋은데이 민트초코", "jot-eun-day-mint-choco", 12.5,
                        createFilePath("jo_eun_day_mint_choco"), 0.0, ETC, "하쯔루는 엄선된 쌀과 정미기술 그리고 롯코산의 깨끗한 물을 이용하여 청아하고 맛의 균형이 잡힌 다이긴죠를 생산해 냅니다. 신맛과 단맛의 균형이 잘 잡힌 술로 목넘김이 부드럽고 담백하고 마일드한 맛이 일품입니다.")

        );

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);
        drinkRepository.saveAll(drinks_spread);

        final Member savedMember = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello"),
                        Biography.create("test")));
        final Member savedMember1 = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello1"),
                        Biography.create("test")));
        final Member savedMember2 = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello2"),
                        Biography.create("test")));
        final Member savedMember3 = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello3"),
                        Biography.create("test")));
        final Member savedMember4 = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello4"),
                        Biography.create("test")));
        final Member savedMember5 = memberRepository.save(Member
                .create(Provider.create("abc", ProviderName.TEST), Nickname.create("hello5"),
                        Biography.create("test")));

        final String token = jwtTokenProvider.createToken(savedMember.getId().toString());
        System.out.println("===============================token========================");
        System.out.println(token);
        System.out.println("===============================token========================");

        for (Drink drink : drinks_spread) {
            preferenceService.createOrUpdatePreference(savedMember.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
            preferenceService.createOrUpdatePreference(savedMember1.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
            preferenceService.createOrUpdatePreference(savedMember2.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
            preferenceService.createOrUpdatePreference(savedMember3.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
            preferenceService.createOrUpdatePreference(savedMember4.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
            preferenceService.createOrUpdatePreference(savedMember5.getId(), drink.getId(), PreferenceDto
                    .create(Math.round((Math.random()*3+2) * 10) / 10.0));
        }
    }

    private ImageFilePath createFilePath(String drinkName) {
        String smallFilePath =
                "https://dmaxaug2ve9od.cloudfront.net/w_200/" + drinkName + "_w200.jpg";
        String mediumFilePath =
                "https://dmaxaug2ve9od.cloudfront.net/w_400/" + drinkName + "_w400.jpg";
        String largeFilePath =
                "https://dmaxaug2ve9od.cloudfront.net/w_600/" + drinkName + "_w600.jpg";

        return ImageFilePath.create(smallFilePath, mediumFilePath, largeFilePath);
    }
}
