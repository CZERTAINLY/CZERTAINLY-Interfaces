package com.czertainly.api.model.core.search;

import java.util.List;

public class SearchFieldDataByGroupDto {

    private List<SearchFieldDataDto> searchFieldData;

    private String groupName;

    public SearchFieldDataByGroupDto(List<SearchFieldDataDto> searchFieldData, String groupName) {
        this.searchFieldData = searchFieldData;
        this.groupName = groupName;
    }

    public List<SearchFieldDataDto> getSearchFieldData() {
        return searchFieldData;
    }

    public void setSearchFieldData(List<SearchFieldDataDto> searchFieldData) {
        this.searchFieldData = searchFieldData;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
