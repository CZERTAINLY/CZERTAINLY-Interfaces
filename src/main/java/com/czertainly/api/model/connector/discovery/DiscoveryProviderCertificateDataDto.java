package com.czertainly.api.model.connector.discovery;

import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

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
			description = "Metadata for the Certificate",
			required = true
	)
	private List<InfoAttribute> meta;

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

	public List<InfoAttribute> getMeta() {
		return meta;
	}

	public void setMeta(List<InfoAttribute> meta) {
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
}
