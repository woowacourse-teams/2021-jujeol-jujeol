package com.jujeol;

import com.jujeol.drink.application.dto.DrinkResponse;
import com.jujeol.drink.domain.Category;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"test"})
public class TestDataLoader implements CommandLineRunner {

    private DrinkRepository drinkRepository;

    public static List<Drink> BEERS;

    public TestDataLoader(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Drink stella = Drink.from(
                "스텔라", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink kgb = Drink.from(
                "KGB", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", Category.BEER);
        Drink estp = Drink.from(
                "ESTP", 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", Category.BEER);
        Drink tiger_rad = Drink.from(
                "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png", Category.BEER);
        Drink tsingtao = Drink.from(
                "TSINGTAO", 12.0, "KakaoTalk_Image_2021-07-08-19-58-18_005.png", Category.BEER);
        Drink apple = Drink.from(
                "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", Category.BEER);
        Drink ob = Drink.from(
                "OB", 85.0, "KakaoTalk_Image_2021-07-08-19-58-22_007.png", Category.BEER);
        Drink tigerLemon = Drink.from(
                "Tiger_Lemon", 4.5, "KakaoTalk_Image_2021-07-08-19-58-22_008.png", Category.BEER);

        List<Drink> beers = Arrays.asList(
                stella, kgb, estp, tiger_rad, tsingtao, apple, ob, tigerLemon);

        BEERS = drinkRepository.saveAll(beers);
    }
}
