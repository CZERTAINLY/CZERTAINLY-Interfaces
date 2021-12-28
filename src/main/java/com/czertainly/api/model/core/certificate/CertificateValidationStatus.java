package com.czertainly.api.model.core.certificate;

public enum CertificateValidationStatus {
    SUCCESS,
    FAILED,
    WARNING,
    REVOKED,
    NOT_CHECKED,
    INVALID,
    EXPIRING,
    EXPIRED
}
