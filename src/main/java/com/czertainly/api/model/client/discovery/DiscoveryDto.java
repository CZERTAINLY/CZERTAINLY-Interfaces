package com.czertainly.api.model.client.discovery;

import java.util.List;

import com.czertainly.api.model.commons.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class DiscoveryDto {
	@Schema(description = "Discovery name", required = true)
	private String name;
	@Schema(description = "List of Attributes for Discovery", required = true)
	private List<RequestAttributeDto> attributes;
	@Schema(description = "Discovery Provider UUID", required = true)
	private String connectorUuid;
	@Schema(description = "Discovery Kind", required = true)
	private String kind;
	
	public String getConnectorUuid() {
		return connectorUuid;
	}
	public void setConnectorUuid(String connectorUuid) {
		this.connectorUuid = connectorUuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RequestAttributeDto> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<RequestAttributeDto> attributes) {
		this.attributes = attributes;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
}
