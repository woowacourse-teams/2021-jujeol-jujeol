package com.jujeol.commons;

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
    private int totalSize;

    public static PageInfo from(int currentPage, int lastPage, int countPerPage, long totalSize) {
        return PageInfo.from(currentPage, lastPage, countPerPage, (int) totalSize);
    }

    public static PageInfo from(int currentPage, int lastPage, int countPerPage, int totalSize) {
        return new PageInfo(currentPage + 1, lastPage, countPerPage, totalSize);
    }
}
