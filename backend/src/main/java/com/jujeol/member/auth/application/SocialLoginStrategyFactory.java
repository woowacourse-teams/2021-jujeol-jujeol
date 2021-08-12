package com.jujeol.member.auth.application;

import com.jujeol.member.auth.domain.ProviderName;

public interface SocialLoginStrategyFactory {

    SocialClient selectSocialClient(ProviderName providerName);
}
