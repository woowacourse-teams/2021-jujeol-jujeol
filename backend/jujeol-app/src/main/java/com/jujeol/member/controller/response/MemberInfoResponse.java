package com.jujeol.member.controller.response;

import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private Long id;
    private String nickname;
    private String bio;

    private MemberInfoResponse(Long id, String nickname, String bio) {
        this.id = id;
        this.nickname = nickname;
        this.bio = bio;
    }

    public static MemberInfoResponse create(Long id, String nickname, String bio) {
        return new MemberInfoResponse(id, nickname, bio);
    }
}
