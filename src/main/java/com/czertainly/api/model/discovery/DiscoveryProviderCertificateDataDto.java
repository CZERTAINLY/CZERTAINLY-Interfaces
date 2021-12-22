package com.czertainly.api.model.discovery;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public class DiscoveryProviderCertificateDataDto {
	
	@Schema(
			description = "Certificate UUID",
			required = true
	)
	private String uuid;
	
	@Schema(
			description = "Base64 encoded Certificate content",
			required = true
	)
	private String base64Content;
	
	@Schema(
			description = "Source of the Discovery",
			required = true
	)
	private String discoverySource;
	
	@Schema(
			description = "Metadata for the Certificate",
			required = true
	)
	private Map<String, Object> meta;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getBase64Content() {
		return base64Content;
	}
	public void setBase64Content(String base64Content) {
		this.base64Content = base64Content;
	}
	public String getDiscoverySource() {
		return discoverySource;
	}
	public void setDiscoverySource(String discoverySource) {
		this.discoverySource = discoverySource;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
	
	
}
