package com.jujeol.oauth.model;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OauthSocialClient implements SocialClient {

    private final List<OauthSocial> oauthSocials;

    @Override
    public MemberDetails getDetails(String socialCode, ProviderName providerName) {
        OauthSocial oauthSocial = oauthSocials.stream()
            .filter(social -> social.isAssignable(providerName))
            .findAny()
            .orElseThrow();
        String accessToken = oauthSocial.getAccessToken(socialCode);
        return oauthSocial.getMemberDetails(accessToken);
    }
}
