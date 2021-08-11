package com.jujeol.member.auth.application;

import com.jujeol.member.auth.domain.ProviderName;

public interface MemberDetails {

    String accountId();

    ProviderName providerCode();
}
