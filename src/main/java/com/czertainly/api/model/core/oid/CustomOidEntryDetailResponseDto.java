package com.czertainly.api.model.core.oid;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomOidEntryDetailResponseDto extends CustomOidEntryResponseDto implements Serializable {

    @Schema(description = "Additional properties depending on the OID category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private AdditionalOidPropertiesDto additionalProperties;
}
