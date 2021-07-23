package com.jujeol.member.ui.dto;

import com.jujeol.member.application.dto.MemberDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberRequest {

    private String nickname;
    private String bio;
}
