package com.otilm.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class AuthenticationServiceExceptionDto {

    @Schema(description = "Status code of the HTTP Request", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusCode;
    @Schema(description = "Code of the result", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
    @Schema(description = "Exception message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    public AuthenticationServiceExceptionDto(Integer statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    public AuthenticationServiceExceptionDto() {
    }

    public AuthenticationServiceExceptionDto(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public AuthenticationServiceExceptionDto(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Falls back to {@code 400} only when no status was set — a payload deserialized from the authentication service
     * may omit the field, and callers pass the result straight to a Spring {@code ResponseEntity} status, which unboxes
     * it.
     */
    public Integer getStatusCode() {
        return statusCode != null ? statusCode : HttpStatus.BAD_REQUEST.value();
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
