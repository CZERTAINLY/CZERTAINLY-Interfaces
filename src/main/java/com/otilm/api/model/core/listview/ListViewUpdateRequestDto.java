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
 * A view restores its columns, its filters and its ordering together. Switching to a view is meant to read as "show me
 * this slice of the inventory", not "keep my current filter but change the headings", so all three are applied as one.
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
    @Schema(description = "Filters the view applies. Absent or empty means the view applies no filter of its own and "
            + "shows the whole inventory.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SearchFilterRequestDto> filters;

    @Valid
    @Schema(description = "Ordering the view applies. Absent means the view falls back to the endpoint's own default "
            + "ordering.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SearchSortRequestDto sort;
}
