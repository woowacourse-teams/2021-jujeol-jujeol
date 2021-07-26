package com.jujeol.member.application;

import com.jujeol.member.application.dto.SocialProviderCodeDto;
import com.jujeol.member.application.dto.TokenDto;
import com.jujeol.member.domain.Biography;
import com.jujeol.member.domain.nickname.CheersExpression;
import com.jujeol.member.domain.nickname.CheersExpressionsRepository;
import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.nickname.Nickname;
import com.jujeol.member.domain.nickname.NicknameCreator;
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
    private final CheersExpressionsRepository cheersExpressionsRepository;
    private final NicknameCreator nicknameCreator;

    @Transactional
    public TokenDto createToken(SocialProviderCodeDto socialProviderCodeDto) {
        final SocialClient socialClient =
                socialLoginStrategyFactory.selectSocialClient(socialProviderCodeDto.getProviderName());

        String accessToken = socialClient.getAccessToken(socialProviderCodeDto.getCode());
        MemberDetails memberDetails = socialClient.getDetails(accessToken);

        Member member = findOrCreateMember(memberDetails);
        final String token = jwtTokenProvider.createToken(member.getId().toString());
        return TokenDto.from(token);
    }

    private Member findOrCreateMember(MemberDetails memberDetails) {
        final String provideId = memberDetails.accountId();

        return memberRepository.findByProvideId(provideId).orElseGet(() -> {
            final Provider provider = Provider.of(provideId, memberDetails.providerCode());
            return memberRepository.save(createMember(provider));
        });
    }

    private Member createMember(Provider provider) {
        final CheersExpression cheersExpression = cheersExpressionsRepository
                .getRandomCheersExpression();

        final Nickname nickname = nicknameCreator.createNickname(cheersExpression.getAbbreviation());
        final Biography biography = Biography.create(cheersExpression.getExpression());

        return Member.create(provider, nickname, biography);
    }
}
