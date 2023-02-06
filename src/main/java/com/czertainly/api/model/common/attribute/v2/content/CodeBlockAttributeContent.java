package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.CodeBlockAttributeContentData;

public class CodeBlockAttributeContent extends BaseAttributeContent<CodeBlockAttributeContentData> {

    private CodeBlockAttributeContentData data;

    public CodeBlockAttributeContent() {
    }

    public CodeBlockAttributeContent(String reference, CodeBlockAttributeContentData data) {
        super(reference, data);
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
