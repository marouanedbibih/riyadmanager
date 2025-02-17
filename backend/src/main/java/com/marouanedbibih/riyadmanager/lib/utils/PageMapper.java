package com.marouanedbibih.riyadmanager.lib.utils;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.marouanedbibih.riyadmanager.lib.models.PageRES;

public class PageMapper {

    public static <T, R> PageRES<R> toPageRES(Page<T> page, Pageable pageable, Function<T, R> mapper) {
        return PageRES.<R>builder()
                .content(page.getContent().stream().map(mapper).toList()) 
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber() + 1)
                .sortBy(pageable.getSort().toString())
                .orderBy(pageable.getSort().isSorted() ? pageable.getSort().iterator().next().getDirection().name() : null)
                .size(page.getSize())
                .build();
    }
    
}