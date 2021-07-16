package com.jujeol.member.ui;

public enum Authority {
    USER, ANONYMOUS;

    public boolean isMember() {
        return USER.equals(this);
    }

    public boolean isAnonymous() {
        return ANONYMOUS.equals(this);
    }
}
