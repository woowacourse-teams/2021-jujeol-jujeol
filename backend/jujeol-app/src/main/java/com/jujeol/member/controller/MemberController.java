package com.jujeol.member.controller;

import com.jujeol.commons.CommonResponse;
import com.jujeol.member.controller.request.UpdateMeRequest;
import com.jujeol.member.controller.response.MemberInfoResponse;
import com.jujeol.member.presenter.MemberPresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberPresenter memberPresenter;

    @GetMapping("/members/me")
    public ResponseEntity<CommonResponse<MemberInfoResponse>> findMemberOfMine(
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(CommonResponse.from(memberPresenter.findMemberInfo(loginMember)));
    }

    @PutMapping("/members/me")
    public ResponseEntity<Void> updateMember(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody UpdateMeRequest updateMeRequest
    ) {
        memberPresenter.updateMe(loginMember, updateMeRequest);
        return ResponseEntity.ok().build();
    }
}
