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
     * The constraints a client has to know are on the {@code @Schema} description rather than only here: this Javadoc
     * does not reach the published specification, and the semantics are the part that is easy to get wrong.
     */
    @Schema(description = """
            Actions the user is permitted to perform on a resource as a whole, per resource. Use this, not \
            allowedListings, to decide whether to offer a control for anything other than a listing.

            Reports resource-level grants only. Object-scoped grants are excluded, including the implicit access an \
            owner or a group member has, because an action held on one object says nothing about the next: a control \
            scoped to a single object must still be decided by the server. Resources on which the caller holds no \
            action are omitted rather than reported with an empty list, and actions the authorization service will \
            not grant at all are never reported.

            This is not derivable from allowedListings and does not supersede it. That field applies a broader rule \
            to the list action alone, unioning object-scoped grants and adding the listings every user gets by \
            default, so neither field can be computed from the other.
            """, requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResourceActionsDto> allowedActions;

}
