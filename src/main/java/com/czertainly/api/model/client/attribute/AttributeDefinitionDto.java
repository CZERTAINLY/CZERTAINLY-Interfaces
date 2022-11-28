package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AttributeDefinitionDto {

    /**
     * Name of the Attribute
     */
    @Schema(description = "UUID of the Attribute", required = true)
    private String uuid;

    /**
     * Name of the Attribute
     */
    @Schema(description = "Name of the Attribute", required = true)
    private String name;

    /**
     * Content Type of the Attribute
     */
    @Schema(description = "Attribute Content Type", required = true)
    private AttributeContentType contentType;

    /**
     * Description of the Attribute
     */
    @Schema(description = "Attribute description", required = true)
    private String description;

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            required = true
    )
    private boolean required;

    /**
     * Boolean determining if the Attribute is enabled..
     **/
    @Schema(
            description = "Boolean determining if the Attribute is enabled.",
            required = true
    )
    private boolean enabled;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("contentType", contentType)
                .append("description", description)
                .append("required", required)
                .append("enabled", enabled)
                .toString();
    }
}
