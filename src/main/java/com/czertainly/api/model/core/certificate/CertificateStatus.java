package com.czertainly.api.model.core.certificate;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateStatus implements IPlatformEnum {

	UNKNOWN("unknown", "Unknown"),
	NEW("new", "New"),
	REJECTED("rejected", "Rejected"),
	VALID("valid", "Valid"),
	INVALID("invalid", "Invalid"),
	REVOKED("revoked", "Revoked"),
	EXPIRING("expiring", "Expiring"),
	EXPIRED("expired", "Expired"),
	;

	private static final CertificateStatus[] VALUES;

	static {
		VALUES = values();
	}

	private final String code;
	private final String label;
	private final String description;

	CertificateStatus(String code, String label) {
		this(code, label,null);
	}

	CertificateStatus(String code, String label, String description) {
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
	public static CertificateStatus findByCode(String code) {
		return Arrays.stream(VALUES)
				.filter(k -> k.code.equals(code))
				.findFirst()
				.orElseThrow(() ->
						new ValidationException(ValidationError.create("Unknown certificate status {}", code)));
	}
}
