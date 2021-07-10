package com.jujeol.member.domain;

import java.time.Year;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BirthYear {

    @Column
    private String birthYear;

    public static BirthYear of(String birthYear) {
        return new BirthYear(birthYear);
    }

    public int currentAge() {
        return Year.now().compareTo(Year.parse(birthYear));
    }
}