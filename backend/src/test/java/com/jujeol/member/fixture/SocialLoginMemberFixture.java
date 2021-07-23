package com.jujeol.member.fixture;

import static java.util.stream.Collectors.toList;

import com.jujeol.member.application.MemberDetails;
import com.jujeol.member.application.dto.SocialProviderCodeDto;
import com.jujeol.member.domain.ProviderName;
import java.util.Arrays;
import java.util.List;

public enum SocialLoginMemberFixture {
    SOLONG("1000", "short.middle.long", "1000"),
    SUNNY("1001", "sunny.sunny.sunny", "1001"),
    TIKE("1002", "tike.tike.tike", "1002"),
    CROFFLE("1003", "cro.ff.le", "1003"),
    WEDGE("1004", "potato.potato.potato", "1004"),
    PIKA("1005", "pika.pika.pika", "1005"),
    NABOM("1006", "nabom.summer.winter", "1006"),
    DEFAULT("1007", "this.is.default", "1007");

    SocialLoginMemberFixture(String matchedCode, String accessToken,
            String accountId) {
        this.matchedCode = matchedCode;
        this.accessToken = accessToken;
        this.memberDetails = new MemberDetails() {
            @Override
            public String accountId() {
                return accountId;
            }

            @Override
            public ProviderName providerCode() {
                return ProviderName.TEST;
            }
        };
    }

    private static List<SocialLoginMemberFixture> FIXTURES =
            Arrays.stream(SocialLoginMemberFixture.values()).collect(toList());

    private String matchedCode;
    private String accessToken;
    private MemberDetails memberDetails;

    public static SocialLoginMemberFixture findByCode(String code) {
        return FIXTURES.stream()
                .filter(member -> member.getMatchedCode().equals(code))
                .findAny()
                .orElse(DEFAULT);
    }

    public static SocialLoginMemberFixture findByToken(String accessToken) {
        return FIXTURES.stream()
                .filter(member -> member.getAccessToken().equals(accessToken))
                .findAny()
                .orElse(DEFAULT);
    }

    public String getMatchedCode() {
        return matchedCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public MemberDetails getMemberDetails() {
        return memberDetails;
    }

    public SocialProviderCodeDto toDto(){
        return SocialProviderCodeDto.of(matchedCode, ProviderName.TEST);
    }
}
