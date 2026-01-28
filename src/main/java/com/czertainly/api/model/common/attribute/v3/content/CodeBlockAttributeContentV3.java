package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.content.data.CodeBlockAttributeContentData;
import com.czertainly.core.util.AttributeDefinitionUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Codeblock attribute content to store encoded snippets of programming language code",
        type = "object")
public class CodeBlockAttributeContentV3 extends BaseAttributeContentV3<CodeBlockAttributeContentData> {

    @Schema(description = "CodeBlock attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private CodeBlockAttributeContentData data;

    public CodeBlockAttributeContentV3() {
    }

    public CodeBlockAttributeContentV3(String reference, CodeBlockAttributeContentData data) {
        super(reference, data);
        this.data = data;
        setContentType(AttributeContentType.CODEBLOCK);
    }

    @Override
    public CodeBlockAttributeContentData getData() {
        return data;
    }

    @Override
    public void setData(CodeBlockAttributeContentData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeBlockAttributeContentV3 that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                data
        );
    }

    @Override
    public CodeBlockAttributeContentData getDataFromDecrypted(String decrypted) {
        return (CodeBlockAttributeContentData) AttributeDefinitionUtils.deserializeContentData(decrypted, CodeBlockAttributeContentData.class);
    }
}
