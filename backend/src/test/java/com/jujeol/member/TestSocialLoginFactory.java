package com.jujeol.member;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.SocialLoginStrategyFactory;
import com.jujeol.member.application.SocialClient;
import com.jujeol.member.domain.ProviderName;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSocialLoginFactory implements SocialLoginStrategyFactory {

    private MemberDetails memberDetails;

    @PostConstruct
    public void initialize() {
        memberDetails = new MemberDetails() {
            @Override
            public String accountId() {
                return "1234";
            }

            @Override
            public String birthYear() {
                return "1993";
            }

            @Override
            public ProviderName providerCode() {
                return ProviderName.TEST;
            }
        };
    }

    @Override
    public SocialClient selectSocialClient(ProviderName providerName) {
        return new SocialClient() {
            @Override
            public String getAccessToken(String code) {
                return null;
            }

            @Override
            public MemberDetails getDetails(String accessToken) {
                return memberDetails;
            }

            @Override
            public void unlink(String accessToken) {
            }
        };
    }
}
