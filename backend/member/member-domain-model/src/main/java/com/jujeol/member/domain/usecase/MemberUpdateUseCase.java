package com.jujeol.member.domain.usecase;

import com.jujeol.member.domain.exception.DuplicatedNicknameException;
import com.jujeol.member.domain.exception.NotExistMemberException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.usecase.command.MemberUpdateCommand;

import java.util.Optional;

public interface MemberUpdateUseCase {

    void update(MemberUpdateCommand command) throws NotExistMemberException, DuplicatedNicknameException;

    interface MemberPort {
        Optional<Member> findById(Long id);
        boolean existsByNickname(String nickname);
        void update(Long id, String nickname, String bio);
    }
}
