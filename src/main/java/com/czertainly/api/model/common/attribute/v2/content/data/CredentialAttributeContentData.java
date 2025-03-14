package com.czertainly.api.model.common.attribute.v2.content.data;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CredentialAttributeContentData extends NameAndUuidDto {

    @Schema(description = "Credential Kind",
            examples = {"SoftKeyStore, Basic, ApiKey, etc"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "List of Credential Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<DataAttribute> attributes;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<DataAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DataAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("kind", kind)
                .append("attributes", attributes)
                .toString();
    }
}
