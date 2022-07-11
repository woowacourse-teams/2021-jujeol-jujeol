package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.rds.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByProvideId(String provideId);

    Optional<MemberEntity> findByProviderNameAndProvideId(ProviderName providerName, String provideId);

    boolean existsByNickname(String nickname);
}