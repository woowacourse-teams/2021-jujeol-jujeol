package com.jujeol.drink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.drink.domain.repository.DrinkRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DrinkRepositoryTest {

    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findAllOrderByViewCountTest() {
        Drink stella = Drink.create(
                "스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", Category.BEER);
        Drink kgb = Drink.create(
                "KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", Category.BEER);
        Drink saveStella = drinkRepository.save(stella);
        Drink saveKgb = drinkRepository.save(kgb);

        saveStella.updateViewCount();
        saveStella.updateViewCount();
        saveStella.updateViewCount();
        saveKgb.updateViewCount();

        testEntityManager.flush();
        testEntityManager.clear();

        Pageable pageable = PageRequest.of(0, 10);

        List<Drink> drinks = drinkRepository.findAllOrderByViewCount(pageable)
                .stream()
                .collect(Collectors.toList());

        assertThat(drinks.get(0)).isEqualTo(saveStella);
        assertThat(drinks.get(0).getViewCount().getViewCount()).isEqualTo(3L);
        assertThat(drinks.get(1)).isEqualTo(saveKgb);
        assertThat(drinks.get(1).getViewCount().getViewCount()).isEqualTo(1L);


    }
}
