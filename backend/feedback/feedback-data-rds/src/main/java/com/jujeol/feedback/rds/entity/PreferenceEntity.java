package com.jujeol.feedback.rds.entity;

import com.jujeol.feedback.domain.model.Preference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceEntity {

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
}
