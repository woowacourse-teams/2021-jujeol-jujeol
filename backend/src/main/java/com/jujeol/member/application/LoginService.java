package com.jujeol.member.application;

import com.jujeol.member.application.dto.TokenRequest;
import com.jujeol.member.application.dto.TokenResponse;
import com.jujeol.member.domain.BirthYear;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final ProviderStrategyFactory providerStrategyFactory;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        final SocialClient socialClient =
                providerStrategyFactory.selectSocialClient(tokenRequest.getProviderName());

        String accessToken = socialClient.getAccessToken(tokenRequest.getCode());
        MemberDetails memberDetails = socialClient.getDetails(accessToken);

        Member member = findOrCreateMember(memberDetails);
        final String token = jwtTokenProvider.createToken(member.getId().toString());
        return new TokenResponse(token);
    }

    private Member findOrCreateMember(MemberDetails memberDetails) {
        final String provideId = memberDetails.accountId();

        return memberRepository.findByProvideId(provideId).orElseGet(() -> {
            final Provider provider = Provider.create(provideId, memberDetails.providerCode());
            final BirthYear birthYear = BirthYear.of(memberDetails.birthYear());
            return memberRepository.save(Member.create(provider, birthYear));
        });
    }
}