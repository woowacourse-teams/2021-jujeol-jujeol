package com.jujeol.member.member.domain.repository;

public interface MemberCustomRepository {

    boolean isExists(String nickname);
}
