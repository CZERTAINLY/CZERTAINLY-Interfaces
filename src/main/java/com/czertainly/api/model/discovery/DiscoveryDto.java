package com.czertainly.api.model.discovery;

import java.util.List;

import com.czertainly.api.model.AttributeDefinition;

public class DiscoveryDto {
	private String name;
	private List<AttributeDefinition> attributes;
	private String connectorUuid;
	private String discoveryType;
	
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
	public List<AttributeDefinition> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeDefinition> attributes) {
		this.attributes = attributes;
	}
	public String getDiscoveryType() {
		return discoveryType;
	}
	public void setDiscoveryType(String discoveryType) {
		this.discoveryType = discoveryType;
	}
}
