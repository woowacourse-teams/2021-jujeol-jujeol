package com.jujeol.feedback.rds.entity;

import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.rds.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private Long drinkId;
    private double rate;

    @Builder
    public PreferenceEntity(Long id, Long memberId, Long drinkId, double rate) {
        this.id = id;
        this.memberId = memberId;
        this.drinkId = drinkId;
        this.rate = rate;
    }

    public Preference toDomain() {
        return Preference.create(id, memberId, drinkId, rate);
    }

    public void updateRate(double rate) {
        this.rate = rate;
    }
}
