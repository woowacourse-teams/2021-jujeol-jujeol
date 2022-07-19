package com.jujeol.member.service;


import com.jujeol.member.domain.exception.NotFoundAuthException;
import com.jujeol.member.domain.model.*;
import com.jujeol.member.domain.reader.MemberReader;
import com.jujeol.member.domain.usecase.AuthLoginUseCase;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.MemberUpdateUseCase;
import com.jujeol.member.domain.usecase.command.AuthLoginCommand;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;
import com.jujeol.member.domain.usecase.command.MemberUpdateCommand;
import com.jujeol.oauth.model.MemberDetails;
import com.jujeol.oauth.model.SocialClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberService {

    private final AuthLoginUseCase authLoginUseCase;
    private final MemberRegisterUseCase memberRegisterUseCase;
    private final MemberUpdateUseCase memberUpdateUseCase;

    private final MemberReader memberReader;

    private final CheersExpressionProvider cheersExpressionProvider;
    private final NicknameCreator nicknameCreator;

    private final SocialClient socialClient;

    public Token loginOrRegister(ProviderName providerName, String code) {
        MemberDetails memberDetails = socialClient.getDetails(code, com.jujeol.oauth.model.ProviderName.valueOf(providerName.name()));
        try {
            return authLoginUseCase.login(AuthLoginCommand.create(Provider.create(memberDetails.getAccountId(), providerName)));
        } catch (NotFoundAuthException e) {
            // 존재하지 않을 경우 회원가입 후 로그인 진행
            CheersExpression cheersExpression = cheersExpressionProvider.getRandomCheersExpression();
            Nickname nickname = nicknameCreator.createNickname(cheersExpression.getAbbreviation());
            MemberRegisterCommand memberRegisterCommand = MemberRegisterCommand.create(
                Provider.create(memberDetails.getAccountId(), providerName), nickname,
                Biography.create(cheersExpression.getExpression())
            );
            memberRegisterUseCase.register(memberRegisterCommand);

            return authLoginUseCase.login(AuthLoginCommand.create(Provider.create(memberDetails.getAccountId(), providerName)));
        }
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberInfo(Long memberId) {
        return memberReader.findById(memberId);
    }

    @Transactional
    public void updateMember(Long id, String nickname, String bio) {
        memberUpdateUseCase.update(MemberUpdateCommand.create(id, Nickname.create(nickname), Biography.create(bio)));
    }
}
