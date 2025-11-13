package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.common.attribute.v2.MetadataAttributeV2;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Objects;

public class DiscoveryProviderCertificateDataDto {
	
	@Schema(
			description = "Certificate UUID",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private String uuid;
	
	@Schema(
			description = "Base64 encoded Certificate content",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private String base64Content;
	
	@Schema(
			description = "Metadata for the Certificate",
			requiredMode = Schema.RequiredMode.REQUIRED
	)
	private List<MetadataAttributeV2> meta;

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

	public List<MetadataAttributeV2> getMeta() {
		return meta;
	}

	public void setMeta(List<MetadataAttributeV2> meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("uuid", uuid)
				.append("base64Content", base64Content)
				.append("meta", meta)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DiscoveryProviderCertificateDataDto)) return false;
		DiscoveryProviderCertificateDataDto that = (DiscoveryProviderCertificateDataDto) o;
		return Objects.equals(base64Content, that.base64Content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(base64Content);
	}
}
