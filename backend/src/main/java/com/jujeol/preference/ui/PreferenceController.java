package com.jujeol.preference.ui;

import com.jujeol.commons.aop.MemberOnly;
import com.jujeol.member.auth.ui.AuthenticationPrincipal;
import com.jujeol.member.auth.ui.LoginMember;
import com.jujeol.preference.application.PreferenceService;
import com.jujeol.preference.ui.dto.PreferenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members/me")
@MemberOnly
public class PreferenceController {

    private final PreferenceService preferenceService;

    @PutMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> createOrUpdatePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId,
            @RequestBody PreferenceRequest preferenceRequest
    ) {
        preferenceService
                .createOrUpdatePreference(loginMember.getId(), drinkId, preferenceRequest.toDto());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> deletePreference(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable(name = "id") Long drinkId
    ) {
        preferenceService.deletePreference(loginMember.getId(), drinkId);
        return ResponseEntity.ok().build();
    }
}
