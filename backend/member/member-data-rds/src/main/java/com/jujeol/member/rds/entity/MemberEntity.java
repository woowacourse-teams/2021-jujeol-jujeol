package com.jujeol.member.rds.entity;

import com.jujeol.member.domain.model.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProviderName providerName;
    private String provideId;
    private String nickname;
    private String biography;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public MemberEntity(Long id, ProviderName providerName, String provideId, String nickname, String biography, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.providerName = providerName;
        this.provideId = provideId;
        this.nickname = nickname;
        this.biography = biography;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        modifiedAt = null;
    }

    @PreUpdate
    private void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }

    public Member toDomain() {
        return Member.create(
            id,
            Provider.create(provideId, providerName),
            Nickname.create(nickname),
            Biography.create(biography)
        );
    }
}
