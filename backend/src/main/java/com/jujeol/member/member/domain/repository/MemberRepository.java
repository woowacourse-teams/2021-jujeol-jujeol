package com.jujeol.member.member.domain.repository;

import com.jujeol.member.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    boolean existsByNicknameNickname(String nickname);

    Optional<Member> findByNicknameNickname(String nickname);
}
