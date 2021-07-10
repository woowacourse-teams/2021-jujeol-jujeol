package com.jujeol.member.application;

import com.jujeol.member.domain.ProviderName;

public interface ProviderStrategyFactory {

    SocialClient selectSocialClient(ProviderName providerName);
}
