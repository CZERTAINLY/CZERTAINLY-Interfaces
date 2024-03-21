package com.czertainly.api.model.core.search;

import com.czertainly.api.model.common.enums.PlatformEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private FilterFieldType type;

    @Schema(description = "List of available conditions for the field",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FilterConditionOperator> conditions;

    @Schema(description = "Platform enum of the field values",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PlatformEnum platformEnum;

    @Schema(description = "Available values for the field")
    private Object value;

    @Schema(description = "Multivalue flag. true = yes, false = no")
    private Boolean multiValue;

    public String getFieldIdentifier() {
        return fieldIdentifier;
    }
    
    @Deprecated
    @JsonIgnore
    public SearchableFields getField() {
        return null;
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

    public FilterFieldType getType() {
        return type;
    }

    public void setType(FilterFieldType type) {
        this.type = type;
    }

    public List<FilterConditionOperator> getConditions() {
        return conditions;
    }

    public void setConditions(List<FilterConditionOperator> conditions) {
        this.conditions = conditions;
    }

    public PlatformEnum getPlatformEnum() {
        return platformEnum;
    }

    public void setPlatformEnum(PlatformEnum platformEnum) {
        this.platformEnum = platformEnum;
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
