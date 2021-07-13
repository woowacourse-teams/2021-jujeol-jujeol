package com.jujeol.drink.application;

import com.jujeol.drink.application.dto.DrinkResponse;
import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.DrinkRepository;
import com.jujeol.drink.exception.NotFoundDrinkException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkService {

    @Value("${file-server.url:localhost:8080/docs}")
    private String fileServerUrl;

    private final DrinkRepository drinkRepository;

    public List<DrinkResponse> showDrinks() {
        //todo: 페이지네이션
        return drinkRepository.findAll(Pageable.ofSize(7))
                .get()
                .map(drink -> DrinkResponse.from(drink, fileServerUrl))
                .collect(Collectors.toList());
    }

    public DrinkResponse showDrinkDetail(Long id) {
        Drink drink = drinkRepository.findById(id)
                .orElseThrow(NotFoundDrinkException::new);
        return DrinkResponse.from(drink, fileServerUrl);
    }
}
