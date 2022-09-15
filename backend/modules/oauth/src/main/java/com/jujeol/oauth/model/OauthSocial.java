package com.jujeol.oauth.model;

public interface OauthSocial {
    boolean isAssignable(ProviderName providerName);

    String getAccessToken(String socialCode);

    MemberDetails getMemberDetails(String accessToken);
}
