package com.czertainly.api.model.common.attribute.v2.constraint;

import com.czertainly.api.model.common.attribute.v2.constraint.data.RangeAttributeConstraintData;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RangeAttributeConstraint extends BaseAttributeConstraint<RangeAttributeConstraintData> {

    @Schema(description = "Integer Range Attribute Constraint Data")
    private RangeAttributeConstraintData data;

    public RangeAttributeConstraint(String description, String errorMessage, RangeAttributeConstraintData data) {
        super(description, errorMessage, AttributeConstraintType.RANGE);
        this.data = data;
    }

    public RangeAttributeConstraint() {
        super(AttributeConstraintType.RANGE);
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
