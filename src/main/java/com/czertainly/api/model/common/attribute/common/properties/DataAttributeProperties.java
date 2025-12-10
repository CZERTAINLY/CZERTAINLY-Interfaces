package com.czertainly.api.model.common.attribute.common.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DataAttributeProperties extends BaseAttributeProperties {

    /**
     * Boolean determining if the Attribute is required. If true, the Attribute must be provided.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is required. If true, the Attribute must be provided.",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean required = false;

    /**
     * Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.
     **/
    @Schema(
            description = "Boolean determining if the Attribute is read only. If true, the Attribute content cannot be changed.",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean readOnly = false;


    /**
     * Boolean determining if the Attribute contains list of values in the content
     **/
    @Schema(
            description = "Boolean determining if the Attribute contains list of values in the content",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean list = false;

    /**
     * Boolean determining if the Attribute can have multiple values
     **/
    @Schema(
            description = "Boolean determining if the Attribute can have multiple values",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean multiSelect = false;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("required", required)
                .append("readOnly", readOnly)
                .append("list", list)
                .append("multi", multiSelect)
                .append("label", getLabel())
                .append("group", getGroup())
                .append("visible", isVisible())
                .toString();
    }
}
