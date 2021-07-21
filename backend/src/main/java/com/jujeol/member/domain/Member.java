package com.jujeol.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Provider provider;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Biography biography;

    public static Member from(Long id, Provider provider, Nickname nickname, Biography biography) {
        return new Member(id, provider, nickname, biography);
    }

    public static Member from(Provider provider, Nickname nickname, Biography biography) {
        return new Member(null, provider, nickname, biography);
    }

    public static Member createAnonymousMember() {
        return new Member(null, null, null, null);
    }
}
