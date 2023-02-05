package com.czertainly.api.model.common.attribute.v2.content.data;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class CodeBlockAttributeContentData extends NameAndUuidDto {

    @Schema(description = "Definition of programming languages used for code",
            example = "JAVA, PHP, C, etc",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private PLanguagesEnum language;

    @Schema(description = "Block of the code. Formatting of the code is specify by variable language",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String codeInBase64;

    public CodeBlockAttributeContentData(final PLanguagesEnum pLanguagesEnum, final String codeInBase64) {
        this.language = pLanguagesEnum;
        this.codeInBase64 = codeInBase64;
    }

    public PLanguagesEnum getLanguage() {
        return language;
    }

    public void setLanguage(PLanguagesEnum language) {
        this.language = language;
    }

    public String getCodeInBase64() {
        return codeInBase64;
    }

    public void setCodeInBase64(String codeInBase64) {
        this.codeInBase64 = codeInBase64;
    }
}
