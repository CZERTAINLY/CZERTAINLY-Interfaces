package com.czertainly.api.model.certificate;

public class CertificateValidationDto {

    private CertificateValidationStatus status;
    private String message;

    public CertificateValidationStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateValidationStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CertificateValidationDto(CertificateValidationStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public CertificateValidationDto() {
    }
}
