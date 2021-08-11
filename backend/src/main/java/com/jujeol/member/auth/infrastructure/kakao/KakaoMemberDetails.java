package com.jujeol.member.auth.infrastructure.kakao;

import com.jujeol.member.auth.application.MemberDetails;
import com.jujeol.member.auth.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoMemberDetails implements MemberDetails {

    private String id;

    @Override
    public String accountId() {
        return id;
    }

    @Override
    public ProviderName providerCode() {
        return ProviderName.KAKAO;
    }
}
