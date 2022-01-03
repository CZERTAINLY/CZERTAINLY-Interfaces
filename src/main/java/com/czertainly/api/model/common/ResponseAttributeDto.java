package com.czertainly.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * This class contains set of properties to represent
 * an Attribute definition including its value for the
 * detail API responses
 */
public class ResponseAttributeDto {

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "166b5cf52-63f2-11ec-90d6-0242ac120003",
            required = true
    )
    private String uuid;

    /**
     * Name of the Attribute, can be used as key for form field label text
     **/
    @Schema(
            description = "Name of the Attribute",
            example = "Attribute",
            required = true
    )
    private String name;

    /**
    * Label of the Attribute, Can be used to display the field name in the User Interface
    **/
   @Schema(
           description = "Label of the the Attribute",
           example = "Attribute Name",
           required = true
   )
   private String label;

    /**
     * Type of the Attribute, base types are defined in {@link BaseAttributeDefinitionTypes}
     **/
    @Schema(
            description = "Type of the Attribute",
            required = true
    )
    private BaseAttributeDefinitionTypes type;

    /**
     * Value of the Attribute, has to be serializable
     **/
    @Schema(
            description = "Value of the Attribute"
    )
    private Serializable value;

    public ResponseAttributeDto() {
        super();
    }

    public ResponseAttributeDto(ResponseAttributeDto original) {
        this.uuid = original.uuid;
        this.name = original.name;
        this.label = original.label;
        this.type = original.type;
        this.value = original.value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BaseAttributeDefinitionTypes getType() {
        return type;
    }

    public void setType(BaseAttributeDefinitionTypes type) {
        this.type = type;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("type", type)
                .append("value", value)
                .toString();
    }
}
