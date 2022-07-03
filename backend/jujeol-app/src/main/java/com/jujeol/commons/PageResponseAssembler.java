package com.jujeol.commons;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseAssembler {

    public static <T> CommonResponse<List<T>> assemble(Page<T> data) {
        PageInfo pageInfo = PageInfo.from(
                data.getPageable().getPageNumber(),
                data.getTotalPages(),
                data.getPageable().getPageSize(),
                data.getTotalElements());
        return CommonResponse.from(data.getContent(), pageInfo);
    }
}
