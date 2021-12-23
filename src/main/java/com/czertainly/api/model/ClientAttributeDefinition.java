package com.czertainly.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * This class contains set of properties to represent
 * an Attribute definition provided by the client
 */
public class ClientAttributeDefinition {

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "166b5cf52-63f2-11ec-90d6-0242ac120003"
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
     * Value of the Attribute, has to be serializable
     **/
    @Schema(
            description = "Value of the Attribute",
            required = true
    )
    private Serializable value;

    public ClientAttributeDefinition() {
        super();
    }

    public ClientAttributeDefinition(ClientAttributeDefinition original) {
        this.uuid = original.uuid;
        this.name = original.name;
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
                .append("value", value)
                .toString();
    }
}
