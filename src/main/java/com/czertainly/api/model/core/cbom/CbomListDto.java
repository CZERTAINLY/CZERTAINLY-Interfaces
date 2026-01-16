package com.czertainly.api.model.core.cbom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CbomListDto {

	@Schema(description = "CBOM serial number (URN)", requiredMode = Schema.RequiredMode.REQUIRED)
	private String serialNumber;

	@Schema(description = "CBOM version", requiredMode = Schema.RequiredMode.REQUIRED)
	private String version;

	@Schema(description = "CBOM spec version", requiredMode = Schema.RequiredMode.REQUIRED)
	private String specVersion;

	@Schema(description = "CBOM timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
	private OffsetDateTime timestamp;

	@Schema(description = "CBOM source (e.g.: CBOM-Lens)", requiredMode = Schema.RequiredMode.REQUIRED)
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
}
