package com.czertainly.api.model.certificate;

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
