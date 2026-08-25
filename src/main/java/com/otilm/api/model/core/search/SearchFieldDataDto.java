package com.otilm.api.model.core.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.enums.PlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class SearchFieldDataDto {
    @Schema(description = "Identifier of field to search", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @Schema(description = "Label for the field", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldLabel;

    @Schema(description = "Type of the field", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldType type;

    @Schema(description = "List of available conditions for the field", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FilterConditionOperator> conditions;

    @Schema(description = "Platform enum of the field values", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private PlatformEnum platformEnum;

    @Schema(description = "Attribute filter field content type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private AttributeContentType attributeContentType;

    @Schema(description = "Available values for the field")
    private Object value;

    @Schema(description = "Multivalue flag. true = yes, false = no")
    private Boolean multiValue;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Whether the field may be requested as a column of the listing. true = yes, false = no",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean displayable;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Whether the listing may be ordered by the field. true = yes, false = no",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean sortable;

    public Boolean isMultiValue() {
        return multiValue;
    }

    public Boolean isDisplayable() {
        return displayable;
    }

    public Boolean isSortable() {
        return sortable;
    }
}
