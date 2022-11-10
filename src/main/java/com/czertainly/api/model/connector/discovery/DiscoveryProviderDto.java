package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import com.czertainly.api.model.core.discovery.DiscoveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

public class DiscoveryProviderDto extends NameAndUuidDto {

	@Schema(description = "Status of Discovery",
			required = true)
	private DiscoveryStatus status;
	
	@Schema(description = "Number of Certificates discovered",
			defaultValue = "0")
	private Integer totalCertificatesDiscovered;

	@Schema(description = "Certificate data",
			required = true)
	private List<DiscoveryProviderCertificateDataDto> certificateData;
	
	@Schema(description = "Certificate Metadata",
			required = true)
	private List<InfoAttribute> meta;

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

	public List<InfoAttribute> getMeta() {
		return meta;
	}

	public void setMeta(List<InfoAttribute> meta) {
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
