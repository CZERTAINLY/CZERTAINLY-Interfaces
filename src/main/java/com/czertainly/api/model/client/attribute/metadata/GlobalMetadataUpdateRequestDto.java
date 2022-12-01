package com.czertainly.api.model.client.attribute.metadata;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GlobalMetadataUpdateRequestDto {

    /**
     * Description of the Attribute
     */
    @Schema(description = "Attribute description", required = true)
    private String description;

    /**
     * Friendly name of the Attribute
     **/
    @Schema(
            description = "Friendly name of the the Attribute",
            example = "Attribute Name",
            required = true
    )
    private String label;

    /**
     * Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.",
            defaultValue = "true"
    )
    private boolean visible = true;


    /**
     * Group of the Attribute, used for the logical grouping of the Attribute
     **/
    @Schema(
            description = "Group of the Attribute, used for the logical grouping of the Attribute",
            example = "requiredAttributes"
    )
    private String group;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("label", label)
                .append("visible", visible)
                .append("group", group)
                .toString();
    }
}
