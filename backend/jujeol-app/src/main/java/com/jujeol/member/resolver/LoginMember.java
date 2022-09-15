package com.jujeol.member.resolver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginMember {

    private Long id;
    private Authority authority;

    public enum Authority {
        USER, ANONYMOUS;

        public boolean isMember() {
            return USER.equals(this);
        }

        public boolean isAnonymous() {
            return ANONYMOUS.equals(this);
        }
    }

    public LoginMember(Authority authority) {
        this(null, authority);
    }

    public static LoginMember anonymous() {
        return new LoginMember(null, Authority.ANONYMOUS);
    }

    public boolean isMember() {
        return authority.isMember();
    }

    public boolean isAnonymous() {
        return authority.isAnonymous();
    }

    public UserAction act() {
        return new UserAction(this);
    }

    public static class UserAction {

        private final LoginMember loginMember;
        private Object returnValue;

        public UserAction(LoginMember loginMember) {
            this.loginMember = loginMember;
        }

        public UserAction ifAnonymous(Supplier<?> supplier) {
            if (loginMember.isAnonymous()) {
                this.returnValue = supplier.get();
            }
            return this;
        }

        public <T extends Throwable> UserAction throwIfAnonymous(Supplier<? extends T> supplier)
                throws T {
            if (loginMember.isAnonymous()) {
                throw supplier.get();
            }
            return this;
        }

        public UserAction ifMember(Function<Long, ?> function) {
            if (loginMember.isMember()) {
                this.returnValue = function.apply(loginMember.getId());
            }
            return this;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        @SuppressWarnings("unchecked")
        public <T> T getReturnValue(Class<T> returnType) {
            return (T) returnValue;
        }
    }
}
