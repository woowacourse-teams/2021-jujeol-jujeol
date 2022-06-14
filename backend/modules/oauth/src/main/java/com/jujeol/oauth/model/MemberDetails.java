package com.jujeol.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetails {

    private String accountId;
    private ProviderName providerName;
}
