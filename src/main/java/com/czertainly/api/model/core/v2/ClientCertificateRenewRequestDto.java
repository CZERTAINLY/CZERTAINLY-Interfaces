package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * Class representing a request to renew certificate
 */
@Data
public class ClientCertificateRenewRequestDto {

    @Schema(
            description = "True to replace renewed certificate in the associated locations",
            defaultValue = "false"
    )
    private boolean replaceInLocations;

    @Schema(
            description = "Certificate sign request (PKCS#10) encoded as Base64 string. If not provided, Existing CSR will be used"
    )
    private String pkcs10;
}
