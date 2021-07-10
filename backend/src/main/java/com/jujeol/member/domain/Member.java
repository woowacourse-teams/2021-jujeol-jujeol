package com.jujeol.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Provider provider;

    @Embedded
    private BirthYear birthYear;


    public static Member create(Provider provider, BirthYear birthYear) {
        // TODO : 미성년자 가입 불가
//        if(birthYear.currentAge() < 19) {
//            throw new MinorAgeException();
//        }
        return new Member(null, provider, birthYear);
    }
}