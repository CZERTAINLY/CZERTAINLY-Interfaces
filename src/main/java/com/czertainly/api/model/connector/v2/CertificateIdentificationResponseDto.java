package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Response containing signed certificate data
 */
@Data
public class CertificateIdentificationResponseDto {
    @Schema(
            description = "Metadata for identified certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<MetadataAttribute> meta;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("meta", meta)
                .toString();
    }
}

