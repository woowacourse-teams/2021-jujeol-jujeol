package com.jujeol.feedback.presenter;

import com.jujeol.exception.ExceptionCodeAndDetails;
import com.jujeol.exception.JujeolBadRequestException;
import com.jujeol.feedback.controller.request.UpdatePreferenceRequest;
import com.jujeol.feedback.service.PreferenceService;
import com.jujeol.member.resolver.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PreferencePresenter {

    private final PreferenceService preferenceService;

    public void createOrUpdatePreference(LoginMember loginMember, Long drinkId,
                                         UpdatePreferenceRequest preferenceRequest) {
        if (loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }
        preferenceService.createOrUpdatePreference(loginMember.getId(), drinkId,
                                                   preferenceRequest.getPreferenceRate());
    }

    public void deletePreference(LoginMember loginMember, Long drinkId) {
        if (loginMember.isAnonymous()) {
            throw new JujeolBadRequestException(ExceptionCodeAndDetails.UNAUTHORIZED_USER);
        }

        preferenceService.deletePreference(loginMember.getId(), drinkId);
    }
}
