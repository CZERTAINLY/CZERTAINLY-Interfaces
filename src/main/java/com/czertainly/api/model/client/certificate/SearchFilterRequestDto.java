package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.search.SearchCondition;
import com.czertainly.api.model.core.search.SearchGroup;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class SearchFilterRequestDto {

    @Schema(description = "Group to search", requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchGroup searchGroup;

    @Schema(description = "Field to search", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @Schema(description = "Condition for the search", requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchCondition condition;

    @Schema(description = "Value to match")
    private Serializable value;


    public String getFieldIdentifier() {
        return fieldIdentifier;
    }

    public SearchCondition getCondition() {
        return condition;
    }

    public Serializable getValue() {
        return value;
    }

    public SearchGroup getSearchGroup() {
        return searchGroup;
    }

}
