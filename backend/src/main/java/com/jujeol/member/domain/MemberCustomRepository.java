package com.jujeol.member.domain;

public interface MemberCustomRepository {

    boolean isExists(String nickname);
}
