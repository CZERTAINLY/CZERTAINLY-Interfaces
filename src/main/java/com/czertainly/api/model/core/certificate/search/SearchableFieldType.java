package com.czertainly.api.model.core.certificate.search;

import java.util.Arrays;

public enum SearchableFieldType {

    STRING(Constants.STRING),
    NUMBER(Constants.NUMBER),
    LIST(Constants.LIST),
    DATE(Constants.DATE)
    ;

    private final String code;

    SearchableFieldType(String string) {
        this.code = string;
    }

    public String getCode() {
        return code;
    }

    public static SearchableFieldType fromCode(String code) {
        return Arrays.stream(values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", code)));
    }

    private static class Constants {
        /** Simple text attribute **/
        private static final String STRING = "string";

        /** Simple number attribute **/
        private static final String NUMBER = "number";

        /** Attribute with predefined value with list of elements **/
        private static final String LIST = "list";

        /** Special attribute type representing credential **/
        private static final String DATE = "date";
    }
}
