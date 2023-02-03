package com.czertainly.api.model.client.cryptography;

import com.czertainly.api.model.core.cryptography.key.KeyDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CryptographicKeyResponseDto {

    @Schema(description = "CryptographicKeys", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<KeyDto> cryptographicKeys;

    @Schema(description = "Number of entries per page", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer itemsPerPage;

    @Schema(description = "Page number for the request", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pageNumber;

    @Schema(description = "Number of pages available", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalPages;

    @Schema(description = "Number of items available", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long totalItems;

    public List<KeyDto> getCryptographicKeys() {
        return cryptographicKeys;
    }

    public void setCryptographicKeys(List<KeyDto> cryptographicKeys) {
        this.cryptographicKeys = cryptographicKeys;
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

    public Integer getTotalPages() {
        return totalPages;
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
