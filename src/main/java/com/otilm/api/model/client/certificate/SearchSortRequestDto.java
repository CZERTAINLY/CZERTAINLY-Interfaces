package com.otilm.api.model.client.certificate;

import com.otilm.api.model.core.search.FilterFieldSource;
import com.otilm.api.model.core.search.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ordering applied to the whole result set before it is paged, so paging walks the sorted set rather than sorting one
 * page. A single field is carried rather than a list because the shared secured-search repository issues one ORDER BY
 * term.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SearchSortRequestDto implements Serializable {

    @NotNull
    @Schema(description = "Field source of the field to sort by", requiredMode = Schema.RequiredMode.REQUIRED)
    private FilterFieldSource fieldSource;

    @NotBlank
    @Schema(description = "Field identifier of the field to sort by. "
            + "Available fields with their identifiers can be retrieved from the resource's "
            + "searchable-fields operation, for example `GET /v1/certificates/search` or "
            + "`GET /v2/connectors/search`; only fields marked as " + "sortable may be sorted by.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String fieldIdentifier;

    @NotNull
    @Schema(description = "Direction of the ordering", requiredMode = Schema.RequiredMode.REQUIRED)
    private SortDirection direction;
}
