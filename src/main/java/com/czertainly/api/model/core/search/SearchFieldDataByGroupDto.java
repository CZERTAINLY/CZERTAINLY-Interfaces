package com.czertainly.api.model.core.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchFieldDataByGroupDto {

    @Schema(description = "Search group", requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchGroup searchGroup;

    @Schema(description = "List of search fields for specified search group", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SearchFieldDataDto> searchFieldData;

    public SearchFieldDataByGroupDto(List<SearchFieldDataDto> searchFieldData, SearchGroup searchGroup) {
        this.searchFieldData = searchFieldData;
        this.searchGroup = searchGroup;
    }

    public List<SearchFieldDataDto> getSearchFieldData() {
        return searchFieldData;
    }

    public void setSearchFieldData(List<SearchFieldDataDto> searchFieldData) {
        this.searchFieldData = searchFieldData;
    }

    public SearchGroup getSearchGroup() {
        return searchGroup;
    }

    public void setSearchGroup(SearchGroup searchGroup) {
        this.searchGroup = searchGroup;
    }
}
