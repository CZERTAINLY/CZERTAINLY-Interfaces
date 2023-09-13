package com.czertainly.api.model.core.certificate;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateValidationStep implements IPlatformEnum {
    CERTIFICATE_CHAIN("certificate_chain", "Signature Verification"),
    SIGNATURE_VERIFICATION("signature", "Signature Verification"),
    CERTIFICATE_VALIDITY("certificate_validity", "Certificate Validity"),
    OCSP_VERIFICATION("ocsp_verification", "OCSP Verification"),
    CRL_VERIFICATION("crl_verification", "CRL Verification");

    private static final CertificateValidationStep[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;


    CertificateValidationStep(String code, String label) {
        this(code, label,null);
    }

    CertificateValidationStep(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static CertificateValidationStep findByCode(String code) {
        return Arrays.stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new ValidationException(ValidationError.create("Unknown certificate validation step {}", code)));
    }


}
