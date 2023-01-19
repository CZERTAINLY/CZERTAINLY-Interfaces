package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.search.SearchCondition;
import com.czertainly.api.model.core.search.SearchableFields;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class SearchFilterRequestDto {
    @Schema(description = "Field to search", requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchableFields field;

    @Schema(description = "Condition for the search", requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchCondition condition;

    @Schema(description = "Value to match")
    private Serializable value;


    public SearchableFields getField() {
        return field;
    }

    public SearchCondition getCondition() {
        return condition;
    }

    public Serializable getValue() {
        return value;
    }
}
