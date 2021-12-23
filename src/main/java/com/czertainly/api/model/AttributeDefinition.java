package com.czertainly.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition including Attribute value.
 */
public class AttributeDefinition {

    /**
     * UUID of Attribute, should by unique
     **/
    @Schema(
            description = "UUID of Attribute, should by unique",
            example = "1",
            required = true
    )
    private String uuid;

    /**
     * Name of Attribute, can be used as key for form field label text
     **/
    @Schema(
            description = "Name of the Attribute",
            example = "Attribute",
            required = true
    )
    private String name;
    
    /**
    * Label of Attribute, Can be used to display the field name in the User Interface
    **/
   @Schema(
           description = "Label of the Attribute",
           example = "Attribute Name",
           required = true
   )
   private String label;

    /**
     * Type of Attribute, base types are defined in {@link BaseAttributeDefinitionTypes}
     **/
    @Schema(
            description = "Type of the Attribute",
            required = true
    )
    private BaseAttributeDefinitionTypes type;

    /**
     * Boolean determining that Attribute is required, value has to be set
     **/
    @Schema(
            description = "Boolean determining that Attribute is required",
            required = true,
            defaultValue = "false"
    )
    private boolean required = false;

    /**
     * Boolean determining that Attribute is read only, value can not be changed
     **/
    @Schema(
            description = "Boolean determining that Attribute is read only",
            required = true,
            defaultValue = "false"
    )
    private boolean readOnly = false;

    /**
     * Boolean determining that Attribute is editable, value can not be edited after is saved
     **/
    @Schema(
            description = "Boolean determining that Attribute is editable",
            required = true,
            defaultValue = "true"
    )
    private boolean editable = true;

    /**
     * Boolean determining that Attribute is visible and can be displayed
     **/
    @Schema(
            description = "Boolean determining that Attribute is visible and can be displayed",
            required = true,
            defaultValue = "true"
    )
    private boolean visible = true;

    /**
     * Boolean determining that Attribute has value composed from multiple items
     **/
    @Schema(
            description = "Boolean determining that Attribute has value composed from multiple items",
            required = true,
            defaultValue = "false"
    )
    private Boolean multiValue = false;

    /**
     * Optional description of Attribute, should contain help for setting proper value
     **/
    @Schema(
            description = "Optional description of Attribute, should contain help for setting proper value",
            required = false
    )
    private String description;

    /**
     * Optional regular expression used for validating Attribute value
     **/
    @Schema(
            description = "Optional regular expression used for validating Attribute value",
            required = false
    )
    private String validationRegex;

    /**
     * Optional list of other Attribute names and values on which this Attribute depends on
     **/
    @Schema(
            description = "Optional list of other Attribute names and values on which this Attribute depends on",
            required = false
    )
    private List<AttributeDependency> dependsOn;

    /**
     * Optional definition of callback for getting needed data
     **/
    @Schema(
            description = "Optional definition of callback for helper methods",
            required = false
    )
    private AttributeCallback AttributeCallback;

    /**
     * Value of Attribute, has to be serializable
     **/
    @Schema(
            description = "Value of Attribute",
            required = false
    )
    private Serializable value;

    public AttributeDefinition() {
        super();
    }

    public AttributeDefinition(AttributeDefinition original) {
        this.uuid = original.uuid;
        this.name = original.name;
        this.label = original.label;
        this.type = original.type;
        this.required = original.required;
        this.readOnly = original.readOnly;
        this.editable = original.editable;
        this.visible = original.visible;
        this.multiValue = original.multiValue;
        this.description = original.description;
        this.validationRegex = original.validationRegex;
        this.dependsOn = original.dependsOn;
        this.AttributeCallback = original.AttributeCallback;
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Boolean isMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Boolean multiValue) {
        this.multiValue = multiValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public void setValidationRegex(String validationRegex) {
        this.validationRegex = validationRegex;
    }

    public List<AttributeDependency> getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(List<AttributeDependency> dependsOn) {
        this.dependsOn = dependsOn;
    }

    public AttributeCallback getAttributeCallback() {
        return AttributeCallback;
    }

    public void setAttributeCallback(AttributeCallback AttributeCallback) {
        this.AttributeCallback = AttributeCallback;
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
                .append("required", required)
                .append("readOnly", readOnly)
                .append("editable", editable)
                .append("visible", visible)
                .append("multiValue", multiValue)
                .append("description", description)
                .append("validationRegex", validationRegex)
                .append("dependsOn", dependsOn)
                .append("AttributeCallback", AttributeCallback)
                .append("value", value)
                .toString();
    }
}
