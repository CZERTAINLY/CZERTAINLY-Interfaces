package com.czertainly.api.model.common.attribute.v2;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * This class defines Attribute types.
 */
@Schema(enumAsRef = true)
public enum AttributeType implements IPlatformEnum {

	DATA(Constants.DATA, "Data"),
    GROUP(Constants.GROUP, "Group"),
    INFO(Constants.INFO, "Info"),
    META(Constants.META, "Metadata"),
    CUSTOM(Constants.CUSTOM, "Custom");

    private static final AttributeType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;

    private final String label;

    private final String description;

    AttributeType(String code, String label) {
        this(code, label,null);
    }

    AttributeType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static AttributeType fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute type %s.", code)));
    }
    
    private static class Constants {
    	/** Data Attributes **/
        private static final String DATA = "data";

        /** Group Attributes **/
        private static final String GROUP = "group";

        /** Info Attributes **/
        private static final String INFO = "info";

        /** Custom Attributes **/
        private static final String CUSTOM = "custom";

        /** Meta Data Attributes **/
        private static final String META = "meta";
    }
}
