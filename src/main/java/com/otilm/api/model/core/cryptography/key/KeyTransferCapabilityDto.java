package com.otilm.api.model.core.cryptography.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Whether key import and export are available for a token profile, and for which key types.
 *
 * <p>
 * The key types belong to the profile rather than the token, because a provider is asked what it accepts per token
 * profile and two profiles on one token can differ. They are keyed by key type so a type cannot appear twice.
 * </p>
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(name = "KeyTransferCapabilityDto",
        description = "Availability of key import and export for a token profile, and the key types each supports")
public class KeyTransferCapabilityDto extends KeyTransferAvailabilityDto {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Algorithms that can be imported, keyed by key type code. Empty when import is not "
            + "available.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<KeyRequestType, Set<KeyAlgorithm>> importableKeyTypes;

    @Schema(description = "Algorithms that can be exported, keyed by key type code. Empty when export is not "
            + "available.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<KeyRequestType, Set<KeyAlgorithm>> exportableKeyTypes;
}
