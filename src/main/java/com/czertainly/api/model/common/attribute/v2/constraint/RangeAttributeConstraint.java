package com.czertainly.api.model.common.attribute.v2.constraint;

import com.czertainly.api.model.common.attribute.v2.constraint.data.RangeAttributeConstraintData;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RangeAttributeConstraint extends BaseAttributeConstraint<RangeAttributeConstraintData> {

    @Schema(description = "Regular Expression Attribute Data")
    private RangeAttributeConstraintData data;

    public RangeAttributeConstraint(String description, String errorMessage, AttributeConstraintType type, RangeAttributeConstraintData data) {
        super(description, errorMessage, type);
        this.data = data;
    }

    public RangeAttributeConstraint() {
    }

    @Override
    public RangeAttributeConstraintData getData() {
        return data;
    }

    public void setData(RangeAttributeConstraintData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("data", data)
                .toString();
    }
}
