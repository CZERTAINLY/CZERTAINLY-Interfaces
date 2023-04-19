package com.czertainly.api.model.core.search;

import java.util.List;

public class SearchFieldDataByGroupDto {

    private List<SearchFieldDataDto> searchFieldData;

    private SearchGroup searchGroup;

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
