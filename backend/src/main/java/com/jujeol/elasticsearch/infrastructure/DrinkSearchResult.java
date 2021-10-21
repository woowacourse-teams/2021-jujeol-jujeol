package com.jujeol.elasticsearch.infrastructure;

import com.jujeol.drink.drink.domain.Drink;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@Getter
public class DrinkSearchResult {

    private List<Drink> drinks;
    private Pageable pageable;
    private Long totalElements;
}
