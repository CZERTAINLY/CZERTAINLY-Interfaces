package com.czertainly.api.model.common.attribute.content;

import com.czertainly.api.model.common.attribute.content.data.FileAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileAttributeContent extends BaseAttributeContent<FileAttributeContentData> {

    @Schema(description = "File Content", required = true)
    private FileAttributeContentData data;

    public FileAttributeContent(String reference, FileAttributeContentData data) {
        super(reference, data);
        this.data = data;
    }

    public FileAttributeContent() {
        super();
    }

    @Override
    public FileAttributeContentData getData() {
        return data;
    }

    @Override
    public void setData(FileAttributeContentData data) {
        this.data = data;
    }
}
