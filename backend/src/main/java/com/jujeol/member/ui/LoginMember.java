package com.jujeol.member.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginMember {

    private Long id;
    private Authority authority;

    public LoginMember(Authority authority) {
        this(null, authority);
    }

    public boolean isMember() {
        return authority.isMember();
    }

    public boolean isAnonymous() {
        return authority.isAnonymous();
    }
}
