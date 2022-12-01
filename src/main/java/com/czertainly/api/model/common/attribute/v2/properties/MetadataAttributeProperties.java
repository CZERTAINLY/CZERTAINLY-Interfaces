package com.czertainly.api.model.common.attribute.v2.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MetadataAttributeProperties extends BaseAttributeProperties {

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Metadata is a global metadata.",
            defaultValue = "false"
    )
    private boolean global = false;

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("global", global)
                .append("label", getLabel())
                .append("group", getGroup())
                .append("visible", isVisible())
                .toString();
    }
}
