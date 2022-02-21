package com.czertainly.api.model.core.certificate.search;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class SearchFieldDataDto {
    @Schema(description = "Field to search",
            required = true)
    private SearchableFields field;

    @Schema(description = "Name of the field to be displayed in the FE",
            required = true)
    private String label;

    @Schema(description = "Type of the field",
            required = true)
    private SearchableFieldType type;

    @Schema(description = "List of available conditions for the field",
            required = true)
    private List<SearchCondition> conditions;

    @Schema(description = "Available values for the field")
    private List<Object> value;

    @Schema(description = "Multivalue falg. true = yes, false = no")
    private Boolean multiValue;

    public SearchableFields getField() {
        return field;
    }

    public void setField(SearchableFields field) {
        this.field = field;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }

    public Boolean getMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }
}
