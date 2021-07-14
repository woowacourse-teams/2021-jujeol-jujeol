package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.member.application.MemberService;
import com.jujeol.member.application.dto.MemberResponse;
import com.jujeol.member.application.dto.PreferenceRequest;
import com.jujeol.member.domain.AuthenticationPrincipal;
import com.jujeol.member.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/me")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<CommonResponseDto<MemberResponse>> findMemberOfMine(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(CommonResponseDto.fromOne(memberService.findMember(loginMember)));
    }

    @PutMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> createOrUpdatePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId,
            @RequestBody PreferenceRequest preferenceRequest
    ) {
        memberService.createOrUpdatePreference(loginMember, drinkId, preferenceRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> deletePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId
    ) {
        memberService.deletePreference(loginMember, drinkId);
        return ResponseEntity.ok().build();
    }
}
