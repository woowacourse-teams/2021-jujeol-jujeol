package com.jujeol.drink.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    private DrinkName name;
    @Embedded
    private AlcoholByVolume alcoholByVolume;
    @Embedded
    private ImageFilePath imageFilePath;

    public static Drink from(String name, Double alcoholByVolume, String imageUrl) {
        return new Drink(null, new DrinkName(name), new AlcoholByVolume(alcoholByVolume), new ImageFilePath(imageUrl));
    }

    public String getName() {
        return name.getName();
    }

    public Double getAlcoholByVolume() {
        return alcoholByVolume.getValue();
    }

    public String getImageFilePath() {
        return imageFilePath.getImageFilePath();
    }
}
