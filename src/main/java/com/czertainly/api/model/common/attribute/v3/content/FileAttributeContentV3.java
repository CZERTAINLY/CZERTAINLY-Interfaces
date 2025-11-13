package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.v2.content.data.FileAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "File attribute content for storing encoded file content with additional info",
        type = "object")
public class FileAttributeContentV3 extends BaseAttributeContentV3<FileAttributeContentData> {

    @Schema(description = "File attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private FileAttributeContentData data;

    public FileAttributeContentV3(String reference, FileAttributeContentData data) {
        super(reference, data);
        this.data = data;
    }

    public FileAttributeContentV3() {
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
        if (!(o instanceof FileAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        FileAttributeContentV3 that = (FileAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
