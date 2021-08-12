package com.jujeol.member.application;

public interface SocialClient {

    MemberDetails getDetails(String socialCode);

    void unlink(String accessToken);
}
