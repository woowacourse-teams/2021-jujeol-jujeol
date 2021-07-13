package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.member.application.MemberService;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.domain.AuthenticationPrincipal;
import com.jujeol.member.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/me")
    public ResponseEntity<CommonResponseDto<MemberResponse>> findMemberOfMine(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(CommonResponseDto.fromOne(memberService.findMember(loginMember)));
    }
}
