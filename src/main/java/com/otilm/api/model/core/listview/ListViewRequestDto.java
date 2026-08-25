package com.otilm.api.model.core.listview;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A new list view. The resource is settable only here: a view belongs to one listing for its whole life, because its
 * columns are resolved against that resource's field catalogue.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListViewRequestDto extends ListViewUpdateRequestDto {

    @NotNull
    @Schema(description = "Resource whose listing the view applies to", examples = {"certificates"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;
}
