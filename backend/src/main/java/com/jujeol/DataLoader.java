package com.jujeol;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev"})
public class DataLoader implements CommandLineRunner {

    private DrinkRepository drinkRepository;

    public DataLoader(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Drink stella = Drink.from("스텔라", 5.5, "stella_artois.png");
        Drink kgb = Drink.from("KGB", 3.5, "kgb.png");
        Drink efes = Drink.from("EFES", 7.5, "efes.png");
        Drink tiger_rad = Drink.from("Tiger_Rad", 9.5, "tiger_raddler_grapefruit.png");
        Drink tsingtao = Drink.from("TSINGTAO", 12.0, "tsingtao.png");
        Drink gom_pyo = Drink.from("gom_pyo", 8.2, "gom_pyo.png");
        Drink ob = Drink.from("OB", 85.0, "ob_lager.png");
        Drink tigerLemon = Drink.from("Tiger_Lemon", 4.5, "tiger_raddler_lemon.png");

        List<Drink> beers = List
                .of(stella, kgb, efes, tiger_rad, tsingtao, gom_pyo, ob, tigerLemon);
        drinkRepository.saveAll(beers);
    }
}

