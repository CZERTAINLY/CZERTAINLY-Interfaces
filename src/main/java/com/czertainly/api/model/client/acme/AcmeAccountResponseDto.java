package com.czertainly.api.model.client.acme;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.acme.AccountStatus;
import com.czertainly.api.model.core.protocol.ProtocolCertificateAssociationsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Set of properties to represent the Account object from ACME.
 */
@Data
public class AcmeAccountResponseDto {

    @Schema(
            description = "ID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"TtrgfYTR6F"}
    )
    private String accountId;
    @Schema(
            description = "UUID of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"6b55de1c-844f-11ec-a8a3-0242ac120002"}
    )
    private String uuid;
    @Schema(
            description = "Enabled flag. enabled=true, disabled=false",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"false"}
    )
    private Boolean enabled;
    @Schema(
            description = "Order count for the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"23"}
    )
    private Integer totalOrders;
    @Schema(
            description = "Number of successful Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"2"}
    )
    private Integer successfulOrders;
    @Schema(
            description = "Number of failed Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"239"}
    )
    private Integer failedOrders;
    @Schema(
            description = "Number of pending Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"24"}
    )
    private Integer pendingOrders;
    @Schema(
            description = "Number of valid Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"23"}
    )
    private Integer validOrders;
    @Schema(
            description = "Number of processing Orders",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"27"}
    )
    private Integer processingOrders;
    @Schema(
            description = "Status of the Account",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"VALID"}
    )
    private AccountStatus status;
    @Schema(
            description = "Contact information",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"mailto: someadmin@domain.com"}
    )
    private List<String> contact;
    @Schema(
            description = "Terms of Service Agreed",
            requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {"true"}
    )
    private Boolean termsOfServiceAgreed;
    @Schema(
            description = "RA Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            examples = {"RA Profile 1"}
    )
    private SimplifiedRaProfileDto raProfile;
    @Schema(
            description = "Name of the ACME Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            examples = {"ACME Profile 1"}
    )
    private String acmeProfileName;
    @Schema(
            description = "UUID of the ACME Profile",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            examples = {"6b55de1c-844f-11ec-a8a3-0242ac120002"}
    )
    private String acmeProfileUuid;

    @Schema(description = "Properties to set for certificates associated with protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ProtocolCertificateAssociationsDto certificateAssociations;

    public boolean isEnabled() {
        return enabled;
    }
}
