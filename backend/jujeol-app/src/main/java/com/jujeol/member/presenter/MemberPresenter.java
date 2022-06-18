package com.jujeol.member.presenter;

import com.jujeol.commons.exception.NotAuthorizedException;
import com.jujeol.member.controller.request.UpdateMeRequest;
import com.jujeol.member.controller.response.MemberInfoResponse;
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
            throw new NotAuthorizedException();
        }
        // NoSuchMemberException::new
        Member foundMember = memberService.findMemberInfo(loginMember.getId())
            .orElseThrow(IllegalArgumentException::new);
        return MemberInfoResponse.create(foundMember.getId(), foundMember.getNickname().value(), foundMember.getBiography().value());
    }

    public void updateMe(LoginMember loginMember, UpdateMeRequest updateMeRequest) {
        if (loginMember.isAnonymous()) {
            throw new NotAuthorizedException();
        }
        memberService.updateMember(loginMember.getId(), updateMeRequest.getNickname(), updateMeRequest.getBio());
    }
}
