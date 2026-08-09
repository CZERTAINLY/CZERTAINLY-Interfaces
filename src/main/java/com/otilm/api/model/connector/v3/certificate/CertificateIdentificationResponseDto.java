package com.otilm.api.model.connector.v3.certificate;

import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(name = "CertificateIdentificationResponseDtoV3")
public class CertificateIdentificationResponseDto {

    @Schema(description = "Connector-defined metadata describing the cert as known to the upstream CA "
            + "(end-entity name, status flags, profile binding, etc.)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<MetadataAttribute> meta;
}
