package com.jujeol.member.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMeRequest {

    private String nickname;
    private String bio;

    public UpdateMeRequest(String nickname, String bio) {
        this.nickname = nickname;
        this.bio = bio;
    }
}
