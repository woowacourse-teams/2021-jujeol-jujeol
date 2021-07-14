package com.jujeol.drink.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of="id")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    private DrinkName name;
    @Embedded
    private DrinkEnglishName englishName;
    @Embedded
    private AlcoholByVolume alcoholByVolume;
    @Embedded
    private ImageFilePath imageFilePath;
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "drink")
    private List<Review> reviews = new ArrayList<>();

    public static Drink from(
            String name,
            String englishName,
            Double alcoholByVolume,
            String imageUrl,
            Category category
    ) {
        return new Drink(
                null,
                new DrinkName(name),
                new DrinkEnglishName(englishName),
                new AlcoholByVolume(alcoholByVolume),
                new ImageFilePath(imageUrl),
                category,
                new ArrayList<>()
                );
    }

    public void addReview(Review review) {
        review.toDrink(this);
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    public String getName() {
        return name.getName();
    }

    public String getEnglishName() {
        return englishName.getEnglishName();
    }

    public Double getAlcoholByVolume() {
        return alcoholByVolume.getValue();
    }

    public String getImageFilePath() {
        return imageFilePath.getImageFilePath();
    }

    public String getCategory() {
        return category.getName();
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
