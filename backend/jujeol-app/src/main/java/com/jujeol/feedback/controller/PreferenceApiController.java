package com.jujeol.feedback.controller;

import com.jujeol.feedback.controller.request.UpdatePreferenceRequest;
import com.jujeol.feedback.presenter.PreferencePresenter;
import com.jujeol.member.resolver.AuthenticationPrincipal;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PreferenceApiController {

    private final PreferencePresenter preferencePresenter;

    @PutMapping("/members/me/drinks/{id}/preference")
    public ResponseEntity<Void> createOrUpdatePreference(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable(name = "id") Long drinkId,
        @RequestBody UpdatePreferenceRequest preferenceRequest
    ) {
        preferencePresenter.createOrUpdatePreference(loginMember, drinkId, preferenceRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/drinks/{id}/preference")
    public ResponseEntity<Void> deletePreference(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable(name = "id") Long drinkId
    ) {
        preferencePresenter.deletePreference(loginMember, drinkId);
        return ResponseEntity.ok().build();
    }
}
