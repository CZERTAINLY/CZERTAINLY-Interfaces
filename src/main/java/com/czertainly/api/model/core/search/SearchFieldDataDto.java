package com.czertainly.api.model.core.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchFieldDataDto {
    @Schema(description = "Identifier of field to search",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @Schema(description = "Label for the field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldLabel;

    @Schema(description = "Type of the field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private SearchableFieldType type;

    @Schema(description = "List of available conditions for the field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SearchCondition> conditions;

    @Schema(description = "Available values for the field")
    private Object value;

    @Schema(description = "Multivalue flag. true = yes, false = no")
    private Boolean multiValue;

    public String getFieldIdentifier() {
        return fieldIdentifier;
    }

    public void setFieldIdentifier(String fieldIdentifier) {
        this.fieldIdentifier = fieldIdentifier;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public SearchableFieldType getType() {
        return type;
    }

    public void setType(SearchableFieldType type) {
        this.type = type;
    }

    public List<SearchCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<SearchCondition> conditions) {
        this.conditions = conditions;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }
}
