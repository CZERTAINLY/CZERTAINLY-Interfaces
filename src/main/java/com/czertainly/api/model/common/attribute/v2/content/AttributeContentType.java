package com.czertainly.api.model.common.attribute.v2.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * This class defines Attribute Content types.
 */
public enum AttributeContentType {

	STRING(Constants.STRING),
    INTEGER(Constants.INTEGER),
	SECRET(Constants.SECRET),
	FILE(Constants.FILE),
	BOOLEAN(Constants.BOOLEAN),
    CREDENTIAL(Constants.CREDENTIAL),
    DATE(Constants.DATE),
    FLOAT(Constants.FLOAT),
    OBJECT(Constants.OBJECT),
    TEXT(Constants.TEXT),
    TIME(Constants.TIME),
    DATETIME(Constants.DATETIME),;

    private final String code;

    AttributeContentType(String string) {
        this.code = string;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static AttributeContentType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }

    public static Class getClass(AttributeContentType code) {
        switch (code) {
            case STRING:
            case SECRET:
            case TEXT:
                return String.class;
            case INTEGER:
                return Integer.class;
            case BOOLEAN:
                return Boolean.class;
            case FLOAT:
                return Float.class;
            default:
                return null;
        }
    }
    
    private static class Constants {
    	/** Simple text Attribute **/
        private static final String STRING = "string";

        /** Simple integer Attribute **/
        private static final String INTEGER = "integer";

        /** String Attribute containing sensitive data **/
        private static final String SECRET = "secret";

        /** Attribute containing file data in Base64 string **/
        private static final String FILE = "file";

        /** Boolean Attribute taking on values {@code true} or {@code false} **/
        private static final String BOOLEAN = "boolean";

        /** Special Attribute type representing Credential **/
        private static final String CREDENTIAL = "credential";

        /** Attribute type representing date only **/
        private static final String DATE = "date";

        /** Attribute type representing float number **/
        private static final String FLOAT = "float";

        /** Attribute type representing general data object **/
        private static final String OBJECT = "object";

        /** Attribute type representing text, which is multiline data **/
        private static final String TEXT = "text";

        /** Attribute type representing time only **/
        private static final String TIME = "time";

        /** Attribute type representing date and time **/
        private static final String DATETIME = "datetime";
    }
}
