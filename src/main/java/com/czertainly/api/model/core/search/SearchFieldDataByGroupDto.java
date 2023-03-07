package com.czertainly.api.model.core.search;

import java.util.List;

public class SearchFieldDataByGroupDto {

    private List<SearchFieldDataDto> searchFieldData;

    private String label;

    public SearchFieldDataByGroupDto(List<SearchFieldDataDto> searchFieldData, String label) {
        this.searchFieldData = searchFieldData;
        this.label = label;
    }

    public List<SearchFieldDataDto> getSearchFieldData() {
        return searchFieldData;
    }

    public void setSearchFieldData(List<SearchFieldDataDto> searchFieldData) {
        this.searchFieldData = searchFieldData;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
