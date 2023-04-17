package com.czertainly.api.model.core.certificate;

import com.czertainly.api.exception.ValidationError;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.czertainly.api.model.connector.cryptography.enums.IAbstractSearchableEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum CertificateStatus implements IPlatformEnum, IAbstractSearchableEnum {

	UNKNOWN("unknown", "Unknown"),
	NEW("new", "New"),
	VALID("valid", "Valid"),
	INVALID("invalid", "Invalid"),
	REVOKED("revoked", "Revoked"),
	EXPIRING("expiring", "Expiring"),
	EXPIRED("expired", "Expired"),
	;

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
		return Arrays.stream(CertificateStatus.values())
				.filter(k -> k.code.equals(code))
				.findFirst()
				.orElseThrow(() ->
						new ValidationException(ValidationError.create("Unknown certificate status {}", code)));
	}

	@Override
	public String getEnumLabel() {
		return code;
	}
}
