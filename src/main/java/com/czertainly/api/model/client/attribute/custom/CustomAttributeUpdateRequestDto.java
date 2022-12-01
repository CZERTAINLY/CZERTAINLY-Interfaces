package com.czertainly.api.model.client.attribute.custom;

import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CustomAttributeUpdateRequestDto {

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

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            defaultValue = "false"
    )
    private boolean required = false;

    /**
     * Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.",
            defaultValue = "false"
    )
    private boolean readOnly = false;


    /**
     * Boolean determining if the Attribute contains list of values in the content
     **/
    @Schema(
            description = "Boolean determining if the Attribute contains list of values in the content",
            defaultValue = "false"
    )
    private boolean list = false;

    /**
     * Boolean determining if the Attribute can have multiple values
     **/
    @Schema(
            description = "Boolean determining if the Attribute can have multiple values",
            defaultValue = "false"
    )
    private boolean multiSelect = false;

    /**
     * Attribute Content
     */
    @Schema(
            description = "Predefined content for the attribute if needed. The content of the Attribute must satisfy the type"
    )
    private List<BaseAttributeContent> content;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public List<BaseAttributeContent> getContent() {
        return content;
    }

    public void setContent(List<BaseAttributeContent> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("label", label)
                .append("visible", visible)
                .append("group", group)
                .append("required", required)
                .append("readOnly", readOnly)
                .append("list", list)
                .append("multiSelect", multiSelect)
                .append("content", content)
                .toString();
    }
}
