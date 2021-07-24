package com.jujeol.drink.application;

import com.jujeol.drink.domain.Drink;
import com.jujeol.drink.domain.ViewCount;
import com.jujeol.drink.domain.repository.ViewCountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ViewCountService {

    private final ViewCountRepository viewCountRepository;

    @Transactional
    public void insertViewCount(ViewCount viewCount) {
        viewCountRepository.save(viewCount);
    }

    @Transactional
    public void updateViewCount(Drink drink) {
        ViewCount viewCount = viewCountRepository.findByDrinkId(drink.getId())
                .orElseThrow(IllegalArgumentException::new);
        drink.updateViewCount(viewCount);
    }
}
