package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.czertainly.api.model.connector.cryptography.enums.CryptographicAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyDataResponseDto {

    @Schema(
            description = "Attributes for the Key"
    )
    private List<MetadataAttribute> keyAttributes;

    @Schema(description = "Cryptographic algorithm of the Key",
            required = true
    )
    private CryptographicAlgorithm cryptographicAlgorithm;

    public List<MetadataAttribute> getKeyAttributes() {
        return keyAttributes;
    }

    public void setKeyAttributes(List<MetadataAttribute> keyAttributes) {
        this.keyAttributes = keyAttributes;
    }

    public CryptographicAlgorithm getCryptographicAlgorithm() {
        return cryptographicAlgorithm;
    }

    public void setCryptographicAlgorithm(CryptographicAlgorithm cryptographicAlgorithm) {
        this.cryptographicAlgorithm = cryptographicAlgorithm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("keyAttributes", keyAttributes)
                .append("cryptographicAlgorithm", cryptographicAlgorithm)
                .toString();
    }
}
