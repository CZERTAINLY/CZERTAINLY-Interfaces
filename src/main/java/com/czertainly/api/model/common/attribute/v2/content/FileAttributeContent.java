package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.FileAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileAttributeContent extends BaseAttributeContent<FileAttributeContentData> {

    @Schema(description = "File attribute content data", required = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileAttributeContent)) return false;
        if (!super.equals(o)) return false;
        FileAttributeContent that = (FileAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
