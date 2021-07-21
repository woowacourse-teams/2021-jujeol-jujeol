package com.jujeol.member.ui;

import com.jujeol.commons.dto.CommonResponse;
import com.jujeol.member.application.MemberService;
import com.jujeol.member.application.dto.MemberDto;
import com.jujeol.member.application.dto.PreferenceDto;
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
    public ResponseEntity<CommonResponse<MemberDto>> findMemberOfMine(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        return ResponseEntity.ok(CommonResponse.from(memberService.findMember(loginMember.getId())));
    }

    @PutMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> createOrUpdatePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId,
            @RequestBody PreferenceDto preferenceDto
    ) {
        memberService.createOrUpdatePreference(loginMember.getId(), drinkId, preferenceDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> deletePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId
    ) {
        memberService.deletePreference(loginMember.getId(), drinkId);
        return ResponseEntity.ok().build();
    }
}
