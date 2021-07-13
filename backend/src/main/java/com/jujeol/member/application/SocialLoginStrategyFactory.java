package com.jujeol.member.application;

import com.jujeol.member.domain.ProviderName;

public interface SocialLoginStrategyFactory {

    SocialClient selectSocialClient(ProviderName providerName);
}
