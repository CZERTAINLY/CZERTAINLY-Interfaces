package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.certificate.search.SearchCondition;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

public class CertificateResponseDto {
    @Schema(description = "Certificates", required = true)
    private List<CertificateDto> certificates;

    @Schema(description = "Number of entries per page", required = true)
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", required = true)
    private Integer pageNumber;

    @Schema(description = "Total number of pages available", required = true)
    private Integer totalPages;

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
}
