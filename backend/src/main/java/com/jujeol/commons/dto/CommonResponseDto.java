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
public class CommonResponseDto<T> {

    private T data;
    private Integer count;

    public static <T> CommonResponseDto<T> fromList(T data, Integer size) {
        return new CommonResponseDto<>(data, size);
    }

    public static <T> CommonResponseDto<T> fromOne(T data) {
        return new CommonResponseDto<>(data, null);
    }
}