package com.jujeol.member.domain;

import com.jujeol.drink.domain.Review;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.provider.provideId = :provideId")
    Optional<Member> findByProvideId(String provideId);
}
