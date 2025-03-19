package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.constraint.DateTimeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RangeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RegexpAttributeConstraint;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute Constraint definition",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeConstraintType.Codes.REGEXP, schema = RegexpAttributeConstraint.class),
                @DiscriminatorMapping(value = AttributeConstraintType.Codes.RANGE, schema = RangeAttributeConstraint.class),
                @DiscriminatorMapping(value = AttributeConstraintType.Codes.DATETIME, schema = DateTimeAttributeConstraint.class)
        },
        oneOf = {
                RegexpAttributeConstraint.class,
                RangeAttributeConstraint.class,
                DateTimeAttributeConstraint.class
        }
//        subTypes = {
//                RegexpAttributeConstraint.class,
//                RangeAttributeConstraint.class,
//                DateTimeAttributeConstraint.class
//        }
)
public interface BaseAttributeConstraintDto {
    @Schema(description = "Description of the constraint")
    String getDescription();

    @Schema(description = "Error message to be displayed for wrong data")
    String getErrorMessage();

    @Schema(description = "Attribute Constraint Type", requiredMode = Schema.RequiredMode.REQUIRED)
    AttributeConstraintType getType();


}
