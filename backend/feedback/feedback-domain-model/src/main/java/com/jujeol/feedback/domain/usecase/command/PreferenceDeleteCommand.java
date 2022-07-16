package com.jujeol.feedback.domain.usecase.command;

import lombok.Getter;

@Getter
public class PreferenceDeleteCommand {

    private final Long drinkId;
    private final Long memberId;

    private PreferenceDeleteCommand(Long drinkId, Long memberId) {
        this.drinkId = drinkId;
        this.memberId = memberId;
    }

    public static PreferenceDeleteCommand create(Long drinkId, Long memberId) {
        return new PreferenceDeleteCommand(drinkId, memberId);
    }
}
