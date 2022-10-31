package com.czertainly.api.model.common.attribute.constraint;

import com.czertainly.api.model.common.attribute.constraint.data.DateTimeAttributeConstraintData;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DateTimeAttributeConstraint extends BaseAttributeConstraint<DateTimeAttributeConstraintData> {

    @Schema(description = "Regular Expression Attribute Data")
    private DateTimeAttributeConstraintData data;

    @Override
    public DateTimeAttributeConstraintData getData() {
        return data;
    }

    public void setData(DateTimeAttributeConstraintData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("data", data)
                .toString();
    }
}
