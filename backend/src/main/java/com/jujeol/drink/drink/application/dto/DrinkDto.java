package com.jujeol.drink.drink.application.dto;

import com.jujeol.drink.category.application.dto.CategoryDto;
import com.jujeol.drink.drink.domain.Drink;
import com.jujeol.preference.domain.Preference;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class DrinkDto {

    private Long id;
    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String smallImageFilePath;
    private String mediumImageFilePath;
    private String largeImageFilePath;
    private CategoryDto categoryDto;
    private double preferenceRate;
    private double preferenceAvg;
    private double expectedPreference;
    private String description;

    public static DrinkDto create(
            Drink drink,
            Preference preference,
            double expectedPreference
    ) {
        return new DrinkDto(drink.getId(),
                drink.getName(),
                drink.getEnglishName(),
                drink.getAlcoholByVolume(),
                drink.getSmallImageFilePath(),
                drink.getMediumImageFilePath(),
                drink.getLargeImageFilePath(),
                CategoryDto.create(drink.getCategory()),
                preference.getRate(),
                drink.getPreferenceAvg(),
                Math.round(expectedPreference*10) / 10.0,
                drink.getDescription()
        );
    }

    public static DrinkDto create(
            Drink drink,
            Preference preference
    ) {
        return create(drink, preference, 0);
    }

    public void addPreferenceRate(Preference preference) {
        preferenceRate = preference.getRate();
    }
}
