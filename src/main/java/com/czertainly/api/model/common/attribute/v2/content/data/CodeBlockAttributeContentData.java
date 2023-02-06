package com.czertainly.api.model.common.attribute.v2.content.data;

import io.swagger.v3.oas.annotations.media.Schema;

public class CodeBlockAttributeContentData {

    @Schema(description = "Definition of programming languages used for code",
            example = "JAVA, PHP, C, etc",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private PLanguagesEnum language;

    @Schema(description = "Block of the code in Base64. Formatting of the code is specify by variable language",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    public CodeBlockAttributeContentData(final PLanguagesEnum pLanguagesEnum, final String code) {
        this.language = pLanguagesEnum;
        this.code = code;
    }

    public PLanguagesEnum getLanguage() {
        return language;
    }

    public void setLanguage(PLanguagesEnum language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
