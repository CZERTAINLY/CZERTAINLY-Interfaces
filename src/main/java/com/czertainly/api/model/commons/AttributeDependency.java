package com.czertainly.api.model.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class AttributeDependency {

    /**
     * Name of depending attribute
     **/
    @Schema(
            description = "Name of depending attribute",
            required = true
    )
    private String name;

    /**
     * Value of depending attribute, which must be matched
     **/
    @Schema(
            description = "Value of depending attribute, which should be matched",
            required = true
    )
    private Serializable value;

    public AttributeDependency() {
    }

    public AttributeDependency(String name, Serializable value) {
        this.name = name;
        this.value = value;
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
                .append("name", name)
                .append("value", value)
                .toString();
    }
}
