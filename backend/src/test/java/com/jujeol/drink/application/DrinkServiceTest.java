package com.jujeol.drink.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.jujeol.drink.application.dto.DrinkResponse;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

class DrinkServiceTest {

    @Autowired
    private DrinkService drinkService;

//    @DisplayName("전체보기 - 성공")
//    @Test
//    void findDrinks() {
//        //given
//        List<DrinkResponse> drinks = drinkService.findDrinks();
//        //when
//        //then
//        assertThat(drinks).hasSize(7);
//    }
}