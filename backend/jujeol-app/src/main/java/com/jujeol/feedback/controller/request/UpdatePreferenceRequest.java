package com.jujeol.feedback.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePreferenceRequest {

    private double preferenceRate;

    @Builder
    public UpdatePreferenceRequest(double preferenceRate) {
        this.preferenceRate = preferenceRate;
    }
}
