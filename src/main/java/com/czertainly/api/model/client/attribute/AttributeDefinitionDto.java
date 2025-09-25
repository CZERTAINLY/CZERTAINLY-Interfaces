package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class AttributeDefinitionDto implements Loggable {

    /**
     * Name of the Attribute
     */
    @Schema(description = "UUID of the Attribute", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    /**
     * Name of the Attribute
     */
    @Schema(description = "Name of the Attribute", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * Content Type of the Attribute
     */
    @Schema(description = "Attribute Content Type", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttributeContentType contentType;

    /**
     * Description of the Attribute
     */
    @Schema(description = "Attribute description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    /**
     * Boolean determining if the Attribute is enabled..
     **/
    @Schema(
            description = "Boolean determining if the Attribute is enabled. Required only for Custom Attribute"
    )
    private Boolean enabled;

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

    public AttributeContentType getContentType() {
        return contentType;
    }

    public void setContentType(AttributeContentType contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("contentType", contentType)
                .append("description", description)
                .append("enabled", enabled)
                .toString();
    }

    @Override
    public Serializable toLogData() {
        return new NameAndUuidDto(this.uuid, this.name);
    }

    @Override
    public List<String> toLogResourceObjectsNames() {
        return List.of(name);
    }

    @Override
    public List<UUID> toLogResourceObjectsUuids() {
        return List.of(UUID.fromString(uuid));
    }
}
