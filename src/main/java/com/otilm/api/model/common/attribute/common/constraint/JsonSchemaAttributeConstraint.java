package com.otilm.api.model.common.attribute.common.constraint;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Restricts a STRING or TEXT attribute value to JSON conforming to an inline JSON Schema (draft 2020-12). The schema
 * document itself is the constraint data. Enforced by {@code AttributeDefinitionUtils.validateConstraints} beside the
 * other constraint types, so a connector declaring it gets local enforcement like any other constraint.
 */
@Getter
@Setter
@Schema(description = "JSON Schema attribute constraint restricting a string value to JSON conforming to an "
        + "inline JSON Schema (draft 2020-12)", type = "object")
public class JsonSchemaAttributeConstraint extends BaseAttributeConstraint<String> {

    @Schema(description = "Inline JSON Schema document the value must conform to")
    private String data;

    public JsonSchemaAttributeConstraint(String description, String errorMessage, String data) {
        super(description, errorMessage, AttributeConstraintType.JSON_SCHEMA);
        this.data = data;
    }

    public JsonSchemaAttributeConstraint() {
        super(AttributeConstraintType.JSON_SCHEMA);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("data", data).toString();
    }
}
