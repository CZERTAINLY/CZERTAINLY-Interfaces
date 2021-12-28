package com.czertainly.api.model.common;

import java.util.Arrays;

/**
 * This class defines base attribute definition types.
 * But because attribute definition type is String
 * it can contain some other value in addition to list below
 */
public enum BaseAttributeDefinitionTypes {
	
	STRING(Constants.STRING),
	SECRET(Constants.SECRET),
	FILE(Constants.FILE),
	BOOLEAN(Constants.BOOLEAN),
	LIST(Constants.LIST),
    CREDENTIAL(Constants.CREDENTIAL);

    private final String code;

    BaseAttributeDefinitionTypes(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static BaseAttributeDefinitionTypes fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }
    
    private static class Constants {
    	/** Simple text attribute **/
        private static final String STRING = "string";

        /** Text attribute containing sensitive data **/
        private static final String SECRET = "secret";

        /** Attribute containing file data in Base64 string **/
        private static final String FILE = "file";

        /** Boolean attribute taking on values {@code true} or {@code false} **/
        private static final String BOOLEAN = "boolean";

        /** Attribute with predefined value with list of elements **/
        private static final String LIST = "list";

        /** Special attribute type representing credential **/
        private static final String CREDENTIAL = "credential";
    }
}
