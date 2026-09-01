package com.otilm.api.model.core.auth;

import com.otilm.core.model.auth.ResourceAction;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The actions the caller holds on one resource.
 * <p>
 * Reported so a client can gate a control on the action that actually guards the endpoint, rather than inferring it
 * from listing access. The authorization service remains the only gate; this is a hint that keeps a control the caller
 * cannot use off the screen.
 */
@Data
@AllArgsConstructor
public class ResourceActionsDto {

    @Schema(description = "Resource the actions are granted on", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;

    @Schema(description = "Actions the user is permitted to perform on the resource",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResourceAction> actions;

}
