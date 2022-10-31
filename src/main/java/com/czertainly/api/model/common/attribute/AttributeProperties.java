package com.czertainly.api.model.common.attribute;

import io.swagger.v3.oas.annotations.media.Schema;

public class AttributeProperties {

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
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            defaultValue = "false",
            required = true
    )
    private boolean required = false;

    /**
     * Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.",
            defaultValue = "false",
            required = true
    )
    private boolean readOnly = false;

    /**
     * Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is visible and can be displayed, otherwise it should be hidden to the user.",
            defaultValue = "true",
            required = true
    )
    private boolean visible = true;

    /**
     * Boolean determining if the Attribute contains list of values in the content
     **/
    @Schema(
            description = "Boolean determining if the Attribute contains list of values in the content",
            defaultValue = "false",
            required = true
    )
    private boolean list = false;

    /**
     * Boolean determining if the Attribute can have multiple values
     **/
    @Schema(
            description = "Boolean determining if the Attribute can have multiple values",
            defaultValue = "false",
            required = true
    )
    private boolean multi = false;

    /**
     * Group of the Attribute, used for the logical grouping of the Attribute
     **/
    @Schema(
            description = "Group of the Attribute, used for the logical grouping of the Attribute",
            example = "requiredAttributes"
    )
    private String group;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
