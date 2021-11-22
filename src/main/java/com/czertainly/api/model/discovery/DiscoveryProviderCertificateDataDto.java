package com.czertainly.api.model.discovery;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

public class DiscoveryProviderCertificateDataDto {
	
	@Schema(
			description = "Unique id of the certificate",
			required = true
	)
	private Long id;
	
	@Schema(
			description = "PEM Certificate centent in base64",
			required = true
	)
	private String base64Content;
	
	@Schema(
			description = "Source of the discovery",
			required = true
	)
	private String discoverySource;
	
	@Schema(
			description = "Metadata for the certificate",
			required = true
	)
	private Map<String, Object> meta;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
