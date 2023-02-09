package com.czertainly.api.model.core.certificate;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateStatus {

	VALID("valid"),
	REVOKED("revoked"),
	EXPIRED("expired"),
	UNKNOWN("unknown"),
	EXPIRING("expiring"),
	NEW("new"),
	INVALID("invalid"),
    ;

	@Schema(description = "Certificate Status",
			example = "valid", requiredMode = Schema.RequiredMode.REQUIRED)
	private String code;

	CertificateStatus(String code) {
		this.code = code;
	}

	@JsonValue
	public String getCode() {
		return this.code;
	}

	@JsonCreator
	public static CertificateStatus findByCode(String code) {
		return Arrays.stream(CertificateStatus.values())
				.filter(k -> k.code.equals(code))
				.findFirst()
				.orElseThrow(() ->
						new ValidationException(ValidationError.create("Unknown status {}", code)));
	}
}
