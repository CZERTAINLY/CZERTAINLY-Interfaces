package com.otilm.api.model.core.cryptography.key;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Whether key import and export are available at all, so a caller can hide a surface nothing supports.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema(name = "KeyTransferAvailabilityDto", description = "Whether key import and export are available")
public class KeyTransferAvailabilityDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Whether key material can be imported into the token or token profile this belongs to",
            requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "false")
    private boolean importAvailable;

    @Schema(description = "Whether key material can be exported from the token or token profile this belongs to",
            requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "false")
    private boolean exportAvailable;
}
