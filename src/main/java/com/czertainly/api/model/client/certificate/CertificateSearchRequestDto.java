package com.czertainly.api.model.client.certificate;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CertificateSearchRequestDto {

    @Schema(description = "Certificate filter input")
    private List<CertificateFilterRequestDto> filters;

    @Schema(description = "Number of entries per page", defaultValue = "10", maximum = "1000")
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", defaultValue = "1")
    private Integer pageNumber;

    public List<CertificateFilterRequestDto> getFilters() {
        return filters;
    }

    public void setFilters(List<CertificateFilterRequestDto> filters) {
        this.filters = filters;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
