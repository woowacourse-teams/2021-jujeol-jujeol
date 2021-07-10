package com.jujeol.member.infrastructure.kakao;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoMemberDetails implements MemberDetails {

    private String id;
    private String birthYear;

    @Override
    public String accountId() {
        return id;
    }

    @Override
    public String birthYear() {
        return birthYear;
    }

    @Override
    public ProviderName providerCode() {
        return ProviderName.KAKAO;
    }
}