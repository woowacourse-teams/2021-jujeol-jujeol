package com.jujeol.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageInfo {

    private int currentPage;
    private int lastPage;
    private int countPerPage;

    public static PageInfo from(int currentPage, int lastPage, int countPerPage) {
        return new PageInfo(currentPage, lastPage, countPerPage);
    }
}
