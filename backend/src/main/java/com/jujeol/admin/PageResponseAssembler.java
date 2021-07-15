package com.jujeol.admin;

import com.jujeol.commons.dto.CommonResponseDto;
import com.jujeol.commons.dto.PageInfo;
import java.util.List;
import org.springframework.data.domain.Page;

public class PageResponseAssembler {

    public static <T> CommonResponseDto<List<T>> assemble(Page<T> data) {
        final List<T> content = data.getContent();
        final PageInfo pageInfo = PageInfo.from(3, 3, 10);
        return CommonResponseDto.from(content, pageInfo);
    }
}
