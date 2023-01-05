package com.czertainly.api.model.common.attribute.v2.constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RegexpAttributeConstraint extends BaseAttributeConstraint<String> {

    @Schema(description = "Regular Expression Attribute Constraint Data")
    private String data;

    public RegexpAttributeConstraint(String description, String errorMessage, String data) {
        super(description, errorMessage, AttributeConstraintType.REGEXP);
        this.data = data;
    }

    public RegexpAttributeConstraint() {
        super(AttributeConstraintType.REGEXP);
    }

    @Override
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("data", data)
                .toString();
    }
}
