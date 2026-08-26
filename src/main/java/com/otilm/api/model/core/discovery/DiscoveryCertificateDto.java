package com.otilm.api.model.core.discovery;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscoveryCertificateDto {
    @Schema(description = "UUID of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    @Schema(description = "UUID of the Certificate in Certificate inventory")
    private String inventoryUuid;

    @Schema(description = "Certificate common name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String commonName;

    @Schema(description = "Certificate Serial Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String serialNumber;

    @Schema(description = "Issuer common name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String issuerCommonName;

    @Schema(description = "Certificate validity start date", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date notBefore;

    @Schema(description = "Certificate expiration date", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date notAfter;

    @Schema(description = "SHA256 thumbprint of the certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fingerprint;

    @Schema(description = "Base64 encoded Certificate content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificateContent;

    @Schema(description = "Boolean representing if the certificate is newly discovered. True - Certificate is newly discovered"
            + "false - Certificate was already available in the inventory", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean newlyDiscovered;

    @Schema(description = "Whether processing of this row has been attempted; processedError conveys the outcome. "
            + "False means the platform reached no verdict for the row, which may still carry a reason.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean processed;

    @Schema(description = "Reason recorded against this row, if any — not only a failure, and not tied to processed. "
            + "A row that imported cleanly can carry one (a certificate imported without all of its public keys "
            + "associated, say), and a row that was never attempted carries one while processed stays false.")
    private String processedError;

}
