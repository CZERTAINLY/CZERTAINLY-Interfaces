package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CreateKeyRequestDto {

    @Schema(description = "List of Token Profile attributes",
            required = true)
    private List<RequestAttributeDto> tokenProfileAttributes;

    @Schema(description = "List of Attributes to create a Key",
            required = true)
    private List<RequestAttributeDto> createKeyAttributes;

    public List<RequestAttributeDto> getTokenProfileAttributes() {
        return tokenProfileAttributes;
    }

    public void setTokenProfileAttributes(List<RequestAttributeDto> tokenProfileAttributes) {
        this.tokenProfileAttributes = tokenProfileAttributes;
    }

    public List<RequestAttributeDto> getCreateKeyAttributes() {
        return createKeyAttributes;
    }

    public void setCreateKeyAttributes(List<RequestAttributeDto> createKeyAttributes) {
        this.createKeyAttributes = createKeyAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("tokenProfileAttributes", tokenProfileAttributes)
                .append("createKeyAttributes", createKeyAttributes)
                .toString();
    }

}
