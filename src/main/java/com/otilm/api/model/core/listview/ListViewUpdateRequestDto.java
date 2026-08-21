package com.otilm.api.model.core.listview;

import com.otilm.api.model.client.certificate.SearchFilterRequestDto;
import com.otilm.api.model.client.certificate.SearchSortRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * Mutable part of a list view. The resource a view belongs to is fixed at creation and therefore absent here.
 *
 * <p>
 * {@code filters} and {@code sort} are part of the stored shape from the outset but are not applied yet: a view
 * restores columns only. Carrying them now means turning them on later adds behaviour instead of migrating every stored
 * view.
 */
@Data
public class ListViewUpdateRequestDto {

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Name of the view, unique per user and resource", examples = {"Expiry watch"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty
    @Valid
    @Schema(description = "Columns of the view, in display order", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ListViewColumnDto> columns;

    @Schema(description = "Whether this view applies when the listing is opened. At most one view per user and "
            + "resource is the default; marking a view default clears the flag on the previous one.",
            defaultValue = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean defaultView;

    @Valid
    @Schema(description = "Filters stored with the view. Accepted and returned unchanged, but not applied when the "
            + "view is used.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SearchFilterRequestDto> filters;

    @Valid
    @Schema(description = "Ordering stored with the view. Accepted and returned unchanged, but not applied when the "
            + "view is used.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SearchSortRequestDto sort;
}
