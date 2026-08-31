package com.otilm.api.model.client.certificate;

import com.otilm.api.model.core.search.FilterConditionOperator;
import com.otilm.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * One filter term of a listing request. The three addressing halves are what the query builder needs to resolve and
 * apply the term; {@code value} is optional because operators such as EMPTY carry none.
 *
 * <p>
 * The constraints are enforced wherever this shape is cascaded into, which today is the stored filters of a list view.
 * The long-standing listing and bulk-operation bodies do not cascade into their {@code filters}, so their wire contract
 * is unchanged.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SearchFilterRequestDto {

    @NotNull
    @Schema(description = "Field group of search filter", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldSource fieldSource;

    @NotBlank
    @Schema(description = "Field identifier of search filter. List of available fields with their identifiers can be retrieved from corresponding endpoint "
            + "the resource's searchable-fields operation, e.g.: [**GET /v1/certificates/search**](../core-certificate/#tag/Certificate-Inventory/operation/getSearchableFieldInformation)",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @NotNull
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
