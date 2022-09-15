package com.jujeol.feedback.domain.usecase.command;

import lombok.Getter;

@Getter
public class PreferenceUpdateCommand {

    private final Long memberId;
    private final Long drinkId;
    private final double preferenceRate;

    public PreferenceUpdateCommand(Long memberId, Long drinkId, double preferenceRate) {
        this.memberId = memberId;
        this.drinkId = drinkId;
        this.preferenceRate = preferenceRate;
    }

    public static PreferenceUpdateCommand create(Long memberId, Long drinkId, double preferenceRate) {
        return new PreferenceUpdateCommand(memberId, drinkId, preferenceRate);
    }

}
