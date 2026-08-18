package com.otilm.api.model.connector.signatures.contentsigning.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Validation material obtained outside the connector and handed to it complete, so it embeds what it is given and
 * fetches only the gaps.
 *
 * <p>
 * This is the reserved seam for a deployment that sources revocation material from the platform or a dedicated fetch
 * service rather than from the connector's own loaders. No platform caller fills it today.
 * </p>
 *
 * <p>
 * Every list must be present even when empty: an absent list would let a connector read "nothing was found" as "nobody
 * looked", which are different situations.
 * </p>
 */
@Getter
@Setter
@ToString
@Schema(name = "ValidationMaterial",
        description = "Validation material obtained outside the connector. Every list must be present; any of them "
                + "may be empty.")
public class ValidationMaterialDto {

    @NotNull(message = "certificates may be an empty list, but must be present")
    @Schema(description = "Certificates already obtained elsewhere, embedded as supplied: DER-encoded X.509, "
            + "base64-encoded in JSON. May be an empty list, but must be present.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotNull(message = "certificates must not contain null items") byte[]> certificates;

    @NotNull(message = "crls may be an empty list, but must be present")
    @Schema(description = "DER-encoded certificate revocation lists, base64-encoded in JSON. May be an empty "
            + "list, but must be present.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotNull(message = "crls must not contain null items") byte[]> crls;

    @NotNull(message = "ocspResponses may be an empty list, but must be present")
    @Schema(description = "DER-encoded OCSP responses, base64-encoded in JSON. May be an empty list, but must be "
            + "present.", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@NotNull(message = "ocspResponses must not contain null items") byte[]> ocspResponses;
}
