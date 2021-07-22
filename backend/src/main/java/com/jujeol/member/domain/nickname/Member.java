package com.jujeol.member.domain.nickname;

import com.jujeol.member.domain.Biography;
import com.jujeol.member.domain.Provider;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        modifiedAt = null;
    }

    public static Member create(Long id, Provider provider, Nickname nickname, Biography biography) {
        return new Member(id, provider, nickname, biography, null, null);
    }

    public static Member create(Provider provider, Nickname nickname, Biography biography) {
        return new Member(null, provider, nickname, biography, null, null);
    }

    public static Member createAnonymousMember() {
        return new Member(null, null, null, null, null, null);
    }

    @PreUpdate
    private void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
