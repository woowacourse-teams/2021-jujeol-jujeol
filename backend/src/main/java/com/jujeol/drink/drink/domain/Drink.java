package com.jujeol.drink.drink.domain;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.review.domain.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column
    private Double preferenceAvg;

    @OneToMany(mappedBy = "drink", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public static Drink create(
            String name,
            String englishName,
            Double alcoholByVolume,
            List<String> imageUrls,
            Double preferenceAvg,
            Category category
    ) {
        return new Drink(
                null,
                new DrinkName(name),
                new DrinkEnglishName(englishName),
                new AlcoholByVolume(alcoholByVolume),
                ImageFilePath.create(imageUrls),
                category,
                preferenceAvg,
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

    public String getSmallImageFilePath() {
        return imageFilePath.getSmallImageFilePath();
    }

    public String getMediumImageFilePath() {
        return imageFilePath.getMediumImageFilePath();
    }

    public String getLargeImageFilePath() {
        return imageFilePath.getLargeImageFilePath();
    }

    public Category getCategory() {
        return category;
    }

    public Double getPreferenceAvg() {
        return preferenceAvg;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void updateInfo(String name,
            String englishName,
            List<String> imageUrls,
            Category category,
            Double alcoholByVolume) {
        this.name = new DrinkName(name);
        this.englishName = new DrinkEnglishName(englishName);
        this.imageFilePath = ImageFilePath.create(imageUrls);
        this.category = category;
        this.alcoholByVolume = new AlcoholByVolume(alcoholByVolume);
    }

    public void updateAverage(Double average) {
        this.preferenceAvg = average;
    }
}
