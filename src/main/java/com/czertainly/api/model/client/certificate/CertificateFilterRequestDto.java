package com.czertainly.api.model.client.certificate;

import com.czertainly.api.model.core.certificate.search.SearchCondition;
import com.czertainly.api.model.core.certificate.search.SearchableFields;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class CertificateFilterRequestDto {
    @Schema(description = "Field of the search", required = true)
    private SearchableFields field;

    @Schema(description = "Condition for the field of search", required = true)
    private SearchCondition condition;

    @Schema(description = "Value for the search")
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
