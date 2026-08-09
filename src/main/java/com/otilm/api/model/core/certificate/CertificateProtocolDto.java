package com.otilm.api.model.core.certificate;

import com.otilm.api.model.core.enums.CertificateProtocol;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
public class CertificateProtocolDto {

    @Schema(description = "Protocol used to issue certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private CertificateProtocol protocol;

    @Schema(description = "UUID of the protocol profile the certificate was issued through. Null when the profile is not known, e.g. when the association predates tracking or the profile was removed.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID protocolProfileUuid;

    @Schema(description = "Additional UUID for use of the protocol, for example ACME Account UUID in case of ACME protocol")
    private UUID additionalProtocolUuid;

}
