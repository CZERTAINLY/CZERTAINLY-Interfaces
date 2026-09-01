package com.otilm.api.model.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfilePermissionsDto {

    @Schema(description = "Allowed resource listings", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Resource> allowedListings;

    /**
     * Carried alongside {@link #allowedListings} because listing access implies no other action, so a client reading
     * only that field cannot tell a viewer from an editor. Narrow actions such as
     * {@code ResourceAction#UPDATE_BRANDING} guard endpoints that {@code UPDATE} does not reach, and without this a
     * client can only offer the control and let the authorization service refuse it.
     * <p>
     * Reports the actions granted on the resource as a whole, which is what a global control needs. Object-scoped
     * grants are deliberately not folded in, including the implicit access an owner or group member has: an action held
     * on one object says nothing about the next, so a per-object control must still be decided by the server. The two
     * fields therefore answer different questions and neither is derivable from the other - {@code allowedListings}
     * additionally carries platform defaults and object-scoped access.
     * <p>
     * Resources on which the caller holds nothing are omitted rather than reported empty, and actions the authorization
     * service will not grant at all are never reported.
     */
    @Schema(description = "Actions the user is permitted to perform on a resource as a whole, per resource",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResourceActionsDto> allowedActions;

}
