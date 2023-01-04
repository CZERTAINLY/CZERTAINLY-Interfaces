package com.czertainly.api.model.common.attribute.v2.constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseAttributeConstraint<T> extends AttributeConstraint {
    @Schema(description = "Description of the constraint")
    private String description;

    @Schema(description = "Error message to be displayed for wrong data")
    private String errorMessage;

    @Schema(description = "Attribute Constraint Type", required = true)
    private AttributeConstraintType type;

    @Schema(description = "Attribute Constraint Data", required = true)
    private T data;

    public BaseAttributeConstraint(String description, String errorMessage, AttributeConstraintType type) {
        this.description = description;
        this.errorMessage = errorMessage;
        this.type = type;
    }

    public BaseAttributeConstraint(AttributeConstraintType type) {
        this.type = type;
    }

    //Empty constructor needed for serialization/deserialization purpose
    public BaseAttributeConstraint() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AttributeConstraintType getType() {
        return type;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("errorMessage", errorMessage)
                .append("type", type)
                .append("data", data)
                .toString();
    }
}
