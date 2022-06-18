package com.jujeol.member.domain.usecase.command;

import com.jujeol.member.domain.model.Biography;
import com.jujeol.member.domain.model.Nickname;
import lombok.Getter;

@Getter
public class MemberUpdateCommand {

    private Long id;
    private Nickname nickname;
    private Biography bio;

    private MemberUpdateCommand(Long id, Nickname nickname, Biography bio) {
        this.id = id;
        this.nickname = nickname;
        this.bio = bio;
    }

    public static MemberUpdateCommand create(Long id, Nickname nickname, Biography bio) {
        return new MemberUpdateCommand(id, nickname, bio);
    }
}
