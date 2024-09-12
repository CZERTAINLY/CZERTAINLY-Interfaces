package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.CodeBlockAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.FileAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.content.data.SecretAttributeContentData;
import com.czertainly.api.model.common.enums.IPlatformEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * This class defines Attribute Content types.
 */
@Schema(enumAsRef = true)
public enum AttributeContentType implements IPlatformEnum {

    STRING(Constants.STRING, "String", StringAttributeContent.class, String.class, true),
    TEXT(Constants.TEXT, "Text", TextAttributeContent.class, String.class, true),
    INTEGER(Constants.INTEGER, "Integer number", IntegerAttributeContent.class, Integer.class, true),
    BOOLEAN(Constants.BOOLEAN, "Boolean", BooleanAttributeContent.class, Boolean.class, true),
    FLOAT(Constants.FLOAT, "Decimal number", FloatAttributeContent.class, Float.class, true),
    DATE(Constants.DATE, "Date", DateAttributeContent.class, LocalDate.class, true),
    TIME(Constants.TIME, "Time", TimeAttributeContent.class, LocalTime.class, true),
    DATETIME(Constants.DATETIME, "DateTime", DateTimeAttributeContent.class, ZonedDateTime.class, true),
    SECRET(Constants.SECRET, "Secret", SecretAttributeContent.class, SecretAttributeContentData.class, false),
    FILE(Constants.FILE, "File", FileAttributeContent.class, FileAttributeContentData.class, false),
    CREDENTIAL(Constants.CREDENTIAL, "Credential", CredentialAttributeContent.class, CredentialAttributeContentData.class, false),
    CODEBLOCK(Constants.CODEBLOCK, "Code block", CodeBlockAttributeContent.class, CodeBlockAttributeContentData.class, false),
    OBJECT(Constants.OBJECT, "Object", ObjectAttributeContent.class, Object.class, false),
    ;

    private static final AttributeContentType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    private final Class<?> contentClass;
    private final Class<?> contentDataClass;

    private final boolean filterByData;

    AttributeContentType(String code, String label, Class<?> contentClass, Class<?> dataJavaClass, boolean filterByData) {
        this(code, label, null, contentClass, dataJavaClass, filterByData);
    }

    AttributeContentType(String code, String label, String description, Class<?> contentClass, Class<?> contentDataClass, boolean filterByData) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.contentClass = contentClass;
        this.contentDataClass = contentDataClass;
        this.filterByData = filterByData;
    }

    @JsonCreator
    public static AttributeContentType fromCode(String code) {
        return Arrays.stream(VALUES)
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute content type %s.", code)));
    }

    public static AttributeContentType fromClass(Class<?> clazz) {
        return clazz.equals(BaseAttributeContent.class) ? null
                : Arrays.stream(VALUES).filter(e -> e.contentClass.equals(clazz))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported attribute content type for class %s.", clazz)));
    }

    public boolean isFilterByData() {
        return filterByData;
    }

    public Class<?> getContentClass() {
        return contentClass;
    }

    @Override
    @JsonValue
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

    public Class<?> getContentDataClass() {
        return contentDataClass;
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

        /**
         * Attribute type representing code block
         **/
        private static final String CODEBLOCK = "codeblock";
    }
}
