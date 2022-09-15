package com.jujeol.member.presenter;

import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.member.controller.request.UpdateMeRequest;
import com.jujeol.member.controller.response.MemberInfoResponse;
import com.jujeol.member.domain.exception.DuplicatedNicknameException;
import com.jujeol.member.domain.exception.NotExistMemberException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.resolver.LoginMember;
import com.jujeol.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberPresenter {

    private final MemberService memberService;

    public MemberInfoResponse findMemberInfo(LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        Member foundMember = memberService.findMemberInfo(loginMember.getId())
            .orElseThrow(() -> new JujeolBadRequestException(ExceptionCodeAndDetails.NO_SUCH_MEMBER));
        return MemberInfoResponse.create(foundMember.getId(), foundMember.getNickname().value(), foundMember.getBiography().value());
    }

    public void updateMe(LoginMember loginMember, UpdateMeRequest updateMeRequest) {
        if (loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        try {
            memberService.updateMember(loginMember.getId(), updateMeRequest.getNickname(), updateMeRequest.getBio());
        } catch (NotExistMemberException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.NO_SUCH_MEMBER);
        } catch (DuplicatedNicknameException e) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.DUPLICATE_NAME);
        }
    }
}
