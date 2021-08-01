package com.jujeol.member.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    @Query("select m from Member m where m.provider.provideId = :provideId")
    Optional<Member> findByProvideId(String provideId);

    @Query("select m from Member m where m.nickname.nickname like :nickname% order by m.createdAt desc")
    List<Member> findOneStartingWithNicknameAndMostRecent(String nickname, Pageable pageable);
}
