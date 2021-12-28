package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.commons.AttributeDefinition;

import java.util.List;
import java.util.Map;

import com.czertainly.api.model.commons.NameAndUuidDto;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

public class DiscoveryProviderDto extends NameAndUuidDto {

	@Schema(description = "Status of Discovery",
			required = true)
	private DiscoveryStatus status;
	
	@Schema(description = "Total number of Certificates discovered",
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
	
	@Schema(description = "Certificate Metadata",
			implementation = Map.class, 
			required = true)
	private Map<String, Object> meta;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", uuid)
				.append("totalCertificatesDiscovered", totalCertificatesDiscovered).toString();
	}
}
