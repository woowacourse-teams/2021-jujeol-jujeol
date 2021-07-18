package com.jujeol.member.application;

import com.jujeol.member.application.dto.TokenRequest;
import com.jujeol.member.application.dto.TokenResponse;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.Provider;
import com.jujeol.member.util.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final SocialLoginStrategyFactory socialLoginStrategyFactory;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse createToken(TokenRequest tokenRequest) {
        final SocialClient socialClient =
                socialLoginStrategyFactory.selectSocialClient(tokenRequest.getProviderName());

        String accessToken = socialClient.getAccessToken(tokenRequest.getCode());
        MemberDetails memberDetails = socialClient.getDetails(accessToken);

        Member member = findOrCreateMember(memberDetails);
        final String token = jwtTokenProvider.createToken(member.getId().toString());
        return TokenResponse.from(token);
    }

    private Member findOrCreateMember(MemberDetails memberDetails) {
        final String provideId = memberDetails.accountId();

        return memberRepository.findByProvideId(provideId).orElseGet(() -> {
            final Provider provider = Provider.of(provideId, memberDetails.providerCode());
            return memberRepository.save(Member.from(provider));
        });
    }
}
