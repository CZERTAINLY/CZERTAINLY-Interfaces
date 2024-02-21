package com.czertainly.api.model.core.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchFieldDataByGroupDto {

    @Schema(description = "Search group", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldSource filterFieldSource;

    @Schema(description = "List of search fields for specified search group", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SearchFieldDataDto> searchFieldData;

    public SearchFieldDataByGroupDto(List<SearchFieldDataDto> searchFieldData, FilterFieldSource filterFieldSource) {
        this.searchFieldData = searchFieldData;
        this.filterFieldSource = filterFieldSource;
    }

    public List<SearchFieldDataDto> getSearchFieldData() {
        return searchFieldData;
    }

    public void setSearchFieldData(List<SearchFieldDataDto> searchFieldData) {
        this.searchFieldData = searchFieldData;
    }

    public FilterFieldSource getSearchGroup() {
        return filterFieldSource;
    }

    public void setSearchGroup(FilterFieldSource filterFieldSource) {
        this.filterFieldSource = filterFieldSource;
    }
}
