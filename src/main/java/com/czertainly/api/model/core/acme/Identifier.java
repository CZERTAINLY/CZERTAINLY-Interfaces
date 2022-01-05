package com.czertainly.api.model.core.acme;

/**
 * This class in the project represents the identifiers used across the ACME implementation
 */
public class Identifier {
    /**
     * Field represents the type of the identifier. This field primarily defined the
     * DNS identifier types
     */
    private String type;

    /**
     * Represents the value of the identifier in {@link Identifier#type}
     * This field is the identifier itself
     */
    private String value;

    public Identifier() {
    }

    /**
     * Below are the default getters and setters for the above defined parameters
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
