package com.jujeol.member.fixture;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.SocialClient;
import com.jujeol.member.application.SocialLoginStrategyFactory;
import com.jujeol.member.domain.ProviderName;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSocialLoginFactory implements SocialLoginStrategyFactory {

    @Override
    public SocialClient selectSocialClient(ProviderName providerName) {
        return new SocialClient() {
            @Override
            public String getAccessToken(String code) {
                return SocialLoginMemberFixture
                        .findByCode(code)
                        .getAccessToken();
            }

            @Override
            public MemberDetails getDetails(String accessToken) {
                return SocialLoginMemberFixture
                        .findByToken(accessToken)
                        .getMemberDetails();
            }

            @Override
            public void unlink(String accessToken) {
            }
        };
    }
}
