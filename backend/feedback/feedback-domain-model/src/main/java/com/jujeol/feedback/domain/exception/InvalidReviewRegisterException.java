package com.jujeol.feedback.domain.exception;

import lombok.Getter;

@Getter
public class InvalidReviewRegisterException extends IllegalArgumentException {

    private final Reason reason;

    public InvalidReviewRegisterException(Reason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }

    @Getter
    public enum Reason {
        ONCE_PER_DAY("하루에 한 번 리뷰를 작성할 수 있습니다."),
        NO_PREFERENCE("리뷰를 작성하기 전 선호도가 작성이 되어야 합니다."),
        ;

        private final String message;

        Reason(String message) {
            this.message = message;
        }
    }
}
