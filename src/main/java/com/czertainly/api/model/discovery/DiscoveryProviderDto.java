package com.czertainly.api.model.discovery;

import com.czertainly.api.model.AttributeDefinition;

import java.util.List;
import java.util.Map;

import com.czertainly.api.model.credential.CredentialDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

public class DiscoveryProviderDto {
	@Schema(description = "Id of the discovery", 
			required = true)
	private Long id;

	@Schema(description = "Name of the discovery", 
			example = "Test discovery", 
			required = true)
	private String name;

	@Schema(description = "Status of discovery", 
			required = true)
	private DiscoveryStatus status;
	
	@Schema(description = "Total number of certificates that are discovered", 
			defaultValue = "0")
	private Integer totalCertificatesDiscovered;
	@Schema(description = "Current Page Number", 
			defaultValue = "0")
	private int pageNumber;
	@Schema(description = "Total number of pages", 
			defaultValue = "0")
	private int totalPages;

	@Schema(description = "Certificate data. (100 per page)", 
			implementation = DiscoveryProviderCertificateDataDto.class, 
			required = true)
	private List<DiscoveryProviderCertificateDataDto> certificateData;
	
	@Schema(description = "Certificate Meta Data", 
			implementation = Map.class, 
			required = true)
	private Map<String, Object> meta;
	
	@Schema(description = "Discovery Provider Attributes", 
			implementation = AttributeDefinition.class,
			required = true)
	private List<AttributeDefinition> attributes;
	
	@Schema(description = "Discovery Provider Connector UUID",
			required = true)
	private String connectorUuid;

	@Schema(
			description = "Credential to authorize certificate discovery",
			implementation = CredentialDto.class,
			required = false
	)
	private CredentialDto credential;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DiscoveryStatus getStatus() {
		return status;
	}

	public void setStatus(DiscoveryStatus status) {
		this.status = status;
	}

	public Integer getTotalCertificatesDiscovered() {
		return totalCertificatesDiscovered;
	}

	public void setTotalCertificatesDiscovered(Integer totalCertificatesDiscovered) {
		this.totalCertificatesDiscovered = totalCertificatesDiscovered;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	public List<DiscoveryProviderCertificateDataDto> getCertificateData() {
		return certificateData;
	}

	public void setCertificateData(List<DiscoveryProviderCertificateDataDto> certificateData) {
		this.certificateData = certificateData;
	}

	public List<AttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeDefinition> attributes) {
		this.attributes = attributes;
	}

	public String getConnectorUuid() {
		return connectorUuid;
	}

	public void setConnectorUuid(String connectorUuid) {
		this.connectorUuid = connectorUuid;
	}

	public CredentialDto getCredential() { return credential; }

	public void setCredential(CredentialDto credential) { this.credential = credential; }

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
				.append("totalCertificatesDiscovered", totalCertificatesDiscovered).toString();
	}
}
