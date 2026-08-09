package com.otilm.api.model.connector.entity;

import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PushCertificateResponseDto {

    @Setter
    @Getter
    @Schema(description = "Certificate metadata")
    private List<MetadataAttribute> certificateMetadata;

    @Schema(description = "Is private key available for the certificate in location")
    private Boolean withKey;

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean hasPrivateKey) {
        this.withKey = hasPrivateKey;
    }

}
