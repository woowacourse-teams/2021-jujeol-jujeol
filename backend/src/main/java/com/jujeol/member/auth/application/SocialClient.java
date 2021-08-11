package com.jujeol.member.auth.application;

public interface SocialClient {

    MemberDetails getDetails(String socialCode);

    void unlink(String accessToken);
}
