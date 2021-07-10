package com.jujeol.member.application;

public interface SocialClient {

    String getAccessToken(String code);

    MemberDetails getDetails(String accessToken);

    void unlink(String accessToken);
}
