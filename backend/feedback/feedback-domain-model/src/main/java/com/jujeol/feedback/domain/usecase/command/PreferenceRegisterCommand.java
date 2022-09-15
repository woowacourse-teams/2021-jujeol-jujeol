package com.jujeol.feedback.domain.usecase.command;

import lombok.Getter;

@Getter
public class PreferenceRegisterCommand {

    private final Long memberId;
    private final Long drinkId;
    private final double preferenceRate;

    public static PreferenceRegisterCommand create(Long memberId, Long drinkId, double preferenceRate) {
        return new PreferenceRegisterCommand(memberId, drinkId, preferenceRate);
    }

    public PreferenceRegisterCommand(Long memberId, Long drinkId, double preferenceRate) {
        this.memberId = memberId;
        this.drinkId = drinkId;
        this.preferenceRate = preferenceRate;
    }
}
