package com.otilm.api.model.client.certificate;

import com.otilm.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Reference to a single field the caller wants returned as a column. A field identifier is unique only within its
 * source, so both halves are needed to address a field.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SearchColumnRequestDto implements Serializable {

    @NotNull
    @Schema(description = "Field source of the column", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldSource fieldSource;

    @NotBlank
    @Schema(description = "Field identifier of the column. Available fields with their identifiers can be retrieved "
            + "from the corresponding endpoint `GET /v1/{resource}/search`; only fields marked as displayable may be "
            + "requested as a column.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;
}
