package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class DestroyKeyRequestDto {

    @Schema(description = "List of Token Profile attributes",
            required = true)
    private List<RequestAttributeDto> tokenProfileAttributes;

    @Schema(
            description = "Attributes for the Key"
    )
    private List<MetadataAttribute> keyAttributes;

    public List<RequestAttributeDto> getTokenProfileAttributes() {
        return tokenProfileAttributes;
    }

    public void setTokenProfileAttributes(List<RequestAttributeDto> tokenProfileAttributes) {
        this.tokenProfileAttributes = tokenProfileAttributes;
    }

    public List<MetadataAttribute> getKeyAttributes() {
        return keyAttributes;
    }

    public void setKeyAttributes(List<MetadataAttribute> keyAttributes) {
        this.keyAttributes = keyAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("tokenProfileAttributes", tokenProfileAttributes)
                .append("keyAttributes", keyAttributes)
                .toString();
    }

}
