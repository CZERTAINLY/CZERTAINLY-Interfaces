package com.czertainly.api.model.core.search;

import io.swagger.v3.oas.annotations.media.Schema;

public class DynamicSearchInternalResponse {
    @Schema(description = "Number of items matching the filter", required = true)
    private Long totalItems;
    @Schema(description = "Number of pages", required = true)
    private Integer totalPages;
    @Schema(description = "Search result", required = true)
    private Object result;

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
