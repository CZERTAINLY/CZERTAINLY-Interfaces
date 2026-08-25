package com.otilm.api.model.core.oid;

import com.otilm.api.model.common.validation.OidFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomOidEntryRequestDto extends CustomOidEntryUpdateRequestDto implements Serializable {

    @Schema(description = "Object Identifier (OID) in dot notation (e.g., 1.2.840.113549.1.1.1)",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "1.2.840.113549.1.1.1")
    @NotBlank
    @Pattern(regexp = OidFormat.REGEX, message = OidFormat.MESSAGE)
    private String oid;

}
