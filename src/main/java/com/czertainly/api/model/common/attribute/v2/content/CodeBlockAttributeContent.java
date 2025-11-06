package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.CodeBlockAttributeContentData;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Codeblock attribute content to store encoded snippets of programming language code",
        type = "object")
public class CodeBlockAttributeContent extends BaseAttributeContent<CodeBlockAttributeContentData> {

    @Schema(description = "CodeBlock attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private CodeBlockAttributeContentData data;

    public CodeBlockAttributeContent() {
    }

    public CodeBlockAttributeContent(String reference, CodeBlockAttributeContentData data) {
        super(reference, data);
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
}
