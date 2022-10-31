package com.czertainly.api.model.common.attribute;

import java.util.Arrays;

/**
 * This class defines Attribute types.
 */
public enum AttributeType {

	DATA(Constants.DATA),
    GROUP(Constants.GROUP),
	INFO(Constants.INFO);

    private final String code;

    AttributeType(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static AttributeType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }
    
    private static class Constants {
    	/** Data Attributes **/
        private static final String DATA = "data";

        /** Group Attributes **/
        private static final String GROUP = "group";

        /** Info Attributes **/
        private static final String INFO = "info";
    }
}
