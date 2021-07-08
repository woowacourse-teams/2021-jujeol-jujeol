package com.jujeol;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private DrinkRepository drinkRepository;

    public DataLoader(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Drink 오비_맥주1 = new Drink("오비 맥주1", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주2 = new Drink("오비 맥주2", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주3 = new Drink("오비 맥주3", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주4 = new Drink("오비 맥주4", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주5 = new Drink("오비 맥주5", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주6 = new Drink("오비 맥주6", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주7 = new Drink("오비 맥주7", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");
        Drink 오비_맥주8 = new Drink("오비 맥주8", 4.5, "/KakaoTalk_Image_2021-07-08-19-58-09_001.png");

        List<Drink> 오비_맥주s = List
                .of(오비_맥주1, 오비_맥주2, 오비_맥주3, 오비_맥주4, 오비_맥주5, 오비_맥주6, 오비_맥주7, 오비_맥주8);
        drinkRepository.saveAll(오비_맥주s);
    }
}

