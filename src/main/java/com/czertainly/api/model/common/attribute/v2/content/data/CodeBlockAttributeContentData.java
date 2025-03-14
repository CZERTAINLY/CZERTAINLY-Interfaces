package com.czertainly.api.model.common.attribute.v2.content.data;

import io.swagger.v3.oas.annotations.media.Schema;

public class CodeBlockAttributeContentData {

    @Schema(description = "Definition of programming languages used for code",
            examples = {"JAVA, PHP, C, etc"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private ProgrammingLanguageEnum language;

    @Schema(description = "Block of the code in Base64. Formatting of the code is specified by variable language",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    public CodeBlockAttributeContentData(final ProgrammingLanguageEnum language, final String code) {
        this.language = language;
        this.code = code;
    }

    public CodeBlockAttributeContentData() {
    }

    public ProgrammingLanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(ProgrammingLanguageEnum language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
