package com.czertainly.api.model.common.attribute.v2.content;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * This class defines Attribute Content types.
 */
@Schema(enumAsRef = true)
public enum AttributeContentType {

    STRING(Constants.STRING, StringAttributeContent.class),
    INTEGER(Constants.INTEGER, IntegerAttributeContent.class),
    SECRET(Constants.SECRET, SecretAttributeContent.class),
    FILE(Constants.FILE, FileAttributeContent.class),
    BOOLEAN(Constants.BOOLEAN, BooleanAttributeContent.class),
    CREDENTIAL(Constants.CREDENTIAL, CredentialAttributeContent.class),
    DATE(Constants.DATE, DateAttributeContent.class),
    FLOAT(Constants.FLOAT, FloatAttributeContent.class),
    OBJECT(Constants.OBJECT, ObjectAttributeContent.class),
    TEXT(Constants.TEXT, TextAttributeContent.class),
    TIME(Constants.TIME, TimeAttributeContent.class),
    DATETIME(Constants.DATETIME, DateTimeAttributeContent.class),
    ;

    private final String code;

    private final Class clazz;

    AttributeContentType(String string, Class clazz) {
        this.code = string;
        this.clazz = clazz;
    }
    @JsonCreator
    public static AttributeContentType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }

    public static AttributeContentType fromClass(Class clazz) {
        return Arrays.stream(values())
                .filter(e -> e.clazz.equals(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type for class %s.", clazz)));
    }

    public static Class getClass(AttributeContentType code) {
        switch (code) {
            case STRING:
            case TEXT:
                return StringAttributeContent.class;
            case SECRET:
                return SecretAttributeContent.class;
            case INTEGER:
                return IntegerAttributeContent.class;
            case BOOLEAN:
                return BooleanAttributeContent.class;
            case FLOAT:
                return FloatAttributeContent.class;
            case CREDENTIAL:
                return CredentialAttributeContent.class;
            case DATE:
                return DateAttributeContent.class;
            case DATETIME:
                return DateTimeAttributeContent.class;
            case FILE:
                return FileAttributeContent.class;
            case OBJECT:
                return ObjectAttributeContent.class;
            case TIME:
                return TimeAttributeContent.class;
            default:
                return null;
        }
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    private static class Constants {
        /**
         * Simple text Attribute
         **/
        private static final String STRING = "string";

        /**
         * Simple integer Attribute
         **/
        private static final String INTEGER = "integer";

        /**
         * String Attribute containing sensitive data
         **/
        private static final String SECRET = "secret";

        /**
         * Attribute containing file data in Base64 string
         **/
        private static final String FILE = "file";

        /**
         * Boolean Attribute taking on values {@code true} or {@code false}
         **/
        private static final String BOOLEAN = "boolean";

        /**
         * Special Attribute type representing Credential
         **/
        private static final String CREDENTIAL = "credential";

        /**
         * Attribute type representing date only
         **/
        private static final String DATE = "date";

        /**
         * Attribute type representing float number
         **/
        private static final String FLOAT = "float";

        /**
         * Attribute type representing general data object
         **/
        private static final String OBJECT = "object";

        /**
         * Attribute type representing text, which is multiline data
         **/
        private static final String TEXT = "text";

        /**
         * Attribute type representing time only
         **/
        private static final String TIME = "time";

        /**
         * Attribute type representing date and time
         **/
        private static final String DATETIME = "datetime";
    }
}
