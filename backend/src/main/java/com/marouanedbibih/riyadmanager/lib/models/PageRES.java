package com.marouanedbibih.riyadmanager.lib.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRES <D> {
    private List<D> content;
    private int currentPage;
    private int size;
    private int totalPages;
    private long totalElements;
    private String sortBy;
    private String orderBy;

}
