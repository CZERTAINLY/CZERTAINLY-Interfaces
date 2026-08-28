package com.otilm.api.model.core.oid.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.otilm.api.model.core.oid.ExtensionValueEncoding;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificateExtensionOidPropertiesDto implements AdditionalOidPropertiesDto {

    @Schema(description = "Whether this extension should be marked critical by default when placed in a certificate",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "defaultCritical is required")
    private Boolean defaultCritical;

    @Schema(description = "ASN.1 encoding used to encode the attribute string value into the extension DER value",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "valueEncoding is required")
    private ExtensionValueEncoding valueEncoding;

    @Schema(description = "Inline JSON Schema (draft 2020-12) describing the shape of the extension's JSON value; "
            + "only applicable when valueEncoding is DER, where the value is authored as a structural ASN.1 JSON "
            + "tree", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String valueSchema;

    @AssertTrue(message = "valueSchema is only applicable when valueEncoding is DER")
    @JsonIgnore
    @Schema(hidden = true)
    private boolean isValueSchemaApplicable() {
        return valueSchema == null || valueEncoding == ExtensionValueEncoding.DER;
    }
}
