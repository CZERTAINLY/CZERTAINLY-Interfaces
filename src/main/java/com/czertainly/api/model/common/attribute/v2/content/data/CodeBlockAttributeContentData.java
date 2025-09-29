package com.czertainly.api.model.common.attribute.v2.content.data;

import com.czertainly.api.exception.ValidationException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CodeBlockAttributeContentData implements AttributeContentData {

    @Schema(description = "Definition of programming languages used for code",
            examples = {"JAVA, PHP, C, etc"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private ProgrammingLanguageEnum language;

    @Schema(description = "Block of the code in Base64. Formatting of the code is specified by variable language",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String code;



    public CodeBlockAttributeContentData(final ProgrammingLanguageEnum language, final String code) {
        this.language = language;
        this.code = code;
    }

    public CodeBlockAttributeContentData() {
    }

    @Override
    public void validate() throws ValidationException {
        if (code == null) {
            throw new ValidationException("Code is not present in code block attribute content data");
        }
        if (language == null) {
            throw new ValidationException("Language is not present in code block attribute content data");
        }
    }
}
