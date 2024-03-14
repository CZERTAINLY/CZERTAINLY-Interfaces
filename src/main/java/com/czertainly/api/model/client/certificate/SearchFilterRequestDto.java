package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.search.FilterFieldSource;
import com.czertainly.api.model.core.search.FilterConditionOperator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchFilterRequestDto {

    @Schema(description = "Field group of search filter", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldSource fieldSource;

    @Schema(description = "Field identifier of search filter. List of available fields with their identifiers can be retrieved from corresponding endpoint " +
            "`GET /v1/{resource}/search`, e.g.: [**GET /v1/certificates/search**](../core-certificate/#tag/Certificate-Inventory/operation/getSearchableFieldInformation)",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @Schema(description = "Condition for the search filter", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterConditionOperator condition;

    @Schema(description = "Value to match")
    private Serializable value;


    public String getFieldIdentifier() {
        return fieldIdentifier;
    }

    public FilterConditionOperator getCondition() {
        return condition;
    }

    public Serializable getValue() {
        return value;
    }

    public FilterFieldSource getFieldSource() {
        return fieldSource;
    }

    public void setFieldSource(FilterFieldSource fieldSource) {
        this.fieldSource = fieldSource;
    }

}
