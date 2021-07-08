package com.jujeol.drink.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private DrinkName name;
    @Embedded
    private AlcoholByVolume alcoholByVolume;
    @Embedded
    private ImageFilePath imageFilePath;

    public Drink(String name, Double alcoholByVolume, String imageUrl) {
        this.name = new DrinkName(name);
        this.alcoholByVolume = new AlcoholByVolume(alcoholByVolume);
        this.imageFilePath = new ImageFilePath(imageUrl);
    }

    public Long getId() {
        return id;
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
