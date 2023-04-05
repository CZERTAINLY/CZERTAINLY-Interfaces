package com.czertainly.api.model.core.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum SearchableFieldType {

    STRING(Constants.STRING),
    NUMBER(Constants.NUMBER),
    LIST(Constants.LIST),
    DATE(Constants.DATE),
    DATETIME(Constants.DATETIME),
    BOOLEAN(Constants.BOOLEAN);

    private final String code;

    SearchableFieldType(String string) {
        this.code = string;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static SearchableFieldType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }

    private static class Constants {
        /**
         * Simple text attribute
         **/
        private static final String STRING = "string";

        /**
         * Simple number attribute
         **/
        private static final String NUMBER = "number";

        /**
         * Attribute with predefined value with list of elements
         **/
        private static final String LIST = "list";

        /**
         * Special attribute type representing credential
         **/
        private static final String DATE = "date";

        /**
         * Special attribute type representing credential
         **/
        private static final String DATETIME = "datetime";

        /**
         * Value to compare the boolean values
         **/
        private static final String BOOLEAN = "boolean";
    }
}
