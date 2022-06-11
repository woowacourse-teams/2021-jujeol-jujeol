package com.jujeol.commons;

import lombok.Getter;

@Getter
public enum BadStatus {

    NOT_EXIST_CATEGORY("존재하지 않는 카테고리입니다.");

    private final String message;

    BadStatus(String message) {
        this.message = message;
    }
}
