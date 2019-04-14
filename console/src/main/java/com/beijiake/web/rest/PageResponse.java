package com.beijiake.web.rest;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {

    public static PageResponse of(Page page) {

        PageResponse pageResponse = new PageResponse();

        pageResponse.page = page.getPageable().getPageNumber();
        pageResponse.pageSize = page.getPageable().getPageSize();
        pageResponse.totalPages = page.getTotalPages();
        pageResponse.totalElements = page.getTotalElements();

        pageResponse.content = page.getContent();

        return pageResponse;

    }

    private List<T> content;

    int page;
    int pageSize;
    int totalPages;
    Long totalElements;
}
