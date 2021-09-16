package com.jujeol.member.fixture;

import com.jujeol.member.auth.application.MemberDetails;
import com.jujeol.member.auth.application.SocialClient;
import com.jujeol.member.auth.application.SocialLoginStrategyFactory;
import com.jujeol.member.auth.domain.ProviderName;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSocialLoginFactory implements SocialLoginStrategyFactory {

    @Override
    public SocialClient selectSocialClient(ProviderName providerName) {
        return new SocialClient() {

            @Override
            public MemberDetails getDetails(String socialCode) {
                return TestMember.findByCode(socialCode).getMemberDetails();
            }

            @Override
            public void unlink(String accessToken) {

            }
        };
    }
}
