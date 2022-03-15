package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.certificate.CertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CertificateResponseDto {
    @Schema(description = "Certificates", required = true)
    private List<CertificateDto> certificates;

    @Schema(description = "Number of entries per page", required = true)
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", required = true)
    private Integer pageNumber;

    @Schema(description = "Number of pages available", required = true)
    private Integer totalPages;

    @Schema(description = "Number of items available", required = true)
    private Long totalItems;

    public List<CertificateDto> getCertificates() {
        return certificates;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setCertificates(List<CertificateDto> certificates) {
        this.certificates = certificates;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
}
