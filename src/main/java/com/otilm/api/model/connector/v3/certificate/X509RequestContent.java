package com.otilm.api.model.connector.v3.certificate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.core.certificate.CertificateKeyUsage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Typed identity content of an X.509 certificate request")
public class X509RequestContent extends CertificateRequestContent {

    @Schema(description = "Ordered subject DN components", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RdnEntry> subject;

    @Schema(description = "Subject Alternative Name entries; SAN is never duplicated in extensions",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<GeneralNameEntry> subjectAltNames;

    @Schema(description = "Requested key usages; the key usage extension (2.5.29.15) is never duplicated in "
            + "extensions. Criticality is not carried here: RFC 5280 4.2.1.3 requires this extension to be "
            + "critical and the platform forces it.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CertificateKeyUsage> keyUsage;

    @Schema(description = "Requested extended key usage purposes as dotted-decimal OIDs; the extended key usage "
            + "extension (2.5.29.37) is never duplicated in extensions",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> extendedKeyUsage;

    @Schema(description = "Requested X.509 extensions, excluding SAN, key usage and extended key usage",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestedExtension> extensions;

    @AssertTrue(message = "At least one of subject, subjectAltNames, keyUsage, extendedKeyUsage or extensions "
            + "must be provided")
    @JsonIgnore
    @Schema(hidden = true)
    public boolean isRequestContentProvided() {
        return notEmpty(subject) || notEmpty(subjectAltNames) || notEmpty(keyUsage) || notEmpty(extendedKeyUsage)
                || notEmpty(extensions);
    }

    private static boolean notEmpty(List<?> values) {
        return values != null && !values.isEmpty();
    }
}
