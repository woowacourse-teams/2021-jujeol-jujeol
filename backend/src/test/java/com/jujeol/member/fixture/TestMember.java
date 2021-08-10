package com.jujeol.member.fixture;

import static java.util.stream.Collectors.toList;

import com.jujeol.member.auth.application.MemberDetails;
import com.jujeol.member.auth.application.dto.SocialProviderCodeDto;
import com.jujeol.member.auth.domain.ProviderName;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public enum TestMember {
    SOLONG("1000", "short.middle.long", "1000"),
    SUNNY("1001", "sunny.sunny.sunny", "1001"),
    TIKE("1002", "tike.tike.tike", "1002"),
    CROFFLE("1003", "cro.ff.le", "1003"),
    WEDGE("1004", "potato.potato.potato", "1004"),
    PIKA("1005", "pika.pika.pika", "1005"),
    NABOM("1006", "nabom.summer.winter", "1006"),
    RANDOM_MEMBER("1234", "this.is.default", "random");

    TestMember(String matchedCode, String accessToken,
            String accountId) {
        this.matchedCode = matchedCode;
        this.accessToken = accessToken;
        this.memberDetails = new MemberDetails() {
            @Override
            public String accountId() {
                if(accountId.equals("random")) {
                    return UUID.randomUUID().toString();
                }
                return accountId;
            }

            @Override
            public ProviderName providerCode() {
                return ProviderName.TEST;
            }
        };
    }

    private static List<TestMember> FIXTURES =
            Arrays.stream(TestMember.values()).collect(toList());

    private String matchedCode;
    private String accessToken;
    private MemberDetails memberDetails;

    public static TestMember findByCode(String code) {
        return FIXTURES.stream()
                .filter(member -> member.getMatchedCode().equals(code))
                .findAny()
                .orElse(RANDOM_MEMBER);
    }

    public static TestMember findByToken(String accessToken) {
        return FIXTURES.stream()
                .filter(member -> member.getAccessToken().equals(accessToken))
                .findAny()
                .orElse(RANDOM_MEMBER);
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
        return SocialProviderCodeDto.create(matchedCode, ProviderName.TEST);
    }
}
