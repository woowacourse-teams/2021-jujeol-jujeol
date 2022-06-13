package com.jujeol.member.domain.usecase;

import com.jujeol.member.domain.exception.NotFoundAuthException;
import com.jujeol.member.domain.model.Token;
import com.jujeol.member.domain.usecase.command.MemberLoginCommand;

public interface MemberLoginUseCase {

    Token login(MemberLoginCommand memberLoginCommand) throws NotFoundAuthException;
}
