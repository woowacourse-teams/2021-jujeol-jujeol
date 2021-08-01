package com.jujeol.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(Include.NON_NULL)
public class CommonResponse<T> {

    private T data;
    private PageInfo pageInfo;

    public static <T> CommonResponse<T> from(T data, PageInfo pageInfo) {
        return new CommonResponse<>(data, pageInfo);
    }

    public static <T> CommonResponse<T> from(T data) {
        return new CommonResponse<>(data, null);
    }

    public static CommonResponse<?> ok() {
        return new CommonResponse<>(null, null);
    }
}
