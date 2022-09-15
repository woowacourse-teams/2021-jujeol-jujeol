package com.jujeol.oauth.model;

import com.jujeol.oauth.exception.OauthAccessException;

public interface SocialClient {

    MemberDetails getDetails(String socialCode, ProviderName providerName) throws OauthAccessException;
}
