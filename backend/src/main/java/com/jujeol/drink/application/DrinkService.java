package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkResponse;
import com.jujeol.drink.domain.DrinkRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {
    @Value("${}")
    private static String fileServerUrl;
    private DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<DrinkResponse> findDrinks() {
        //todo: 페이지네이션
        return drinkRepository.findAll(Pageable.ofSize(7))
                .get()
                .map(drink -> DrinkResponse.from(drink, fileServerUrl))
                .collect(Collectors.toList());
    }
}
