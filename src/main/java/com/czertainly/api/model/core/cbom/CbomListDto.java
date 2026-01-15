package com.czertainly.api.model.core.cbom;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public class CbomListDto {

	@Schema(description = "Cbom serial number (URN)", requiredMode = Schema.RequiredMode.REQUIRED)
	private String serialNumber;

	@Schema(description = "Cbom version", requiredMode = Schema.RequiredMode.REQUIRED)
	private String version;

	@Schema(description = "Cbom spec version", requiredMode = Schema.RequiredMode.REQUIRED)
	private String specVersion;

	@Schema(description = "Cbom timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
	private OffsetDateTime timestamp;

	@Schema(description = "Cbom source (e.g.: Cbom-Lens)", requiredMode = Schema.RequiredMode.REQUIRED)
	private String source;

	@Schema(description = "Number of algorithms", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer algorithms;

	@Schema(description = "Number of certificates", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer certificates;

	@Schema(description = "Number of protocols", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer protocols;

	@Schema(description = "Number of crypto material items", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer cryptoMaterial;

	@Schema(description = "Total number of assets", requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer totalAssets;

	// Getters and setters

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(Integer algorithms) {
		this.algorithms = algorithms;
	}

	public Integer getCertificates() {
		return certificates;
	}

	public void setCertificates(Integer certificates) {
		this.certificates = certificates;
	}

	public Integer getProtocols() {
		return protocols;
	}

	public void setProtocols(Integer protocols) {
		this.protocols = protocols;
	}

	public Integer getCryptoMaterial() {
		return cryptoMaterial;
	}

	public void setCryptoMaterial(Integer cryptoMaterial) {
		this.cryptoMaterial = cryptoMaterial;
	}

	public Integer getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(Integer totalAssets) {
		this.totalAssets = totalAssets;
	}
}
