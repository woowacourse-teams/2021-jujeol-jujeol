package com.jujeol.drink.application;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.repository.ViewCountRepository;
import com.jujeol.drink.exception.NotFoundViewCountException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ViewCountService {

    private final ViewCountRepository viewCountRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateViewCount(Drink drink) {
        viewCountRepository.findByDrinkId(drink.getId())
                .orElseThrow(NotFoundViewCountException::new);
        drink.updateViewCount();
    }
}
