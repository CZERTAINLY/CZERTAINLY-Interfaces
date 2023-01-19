package com.czertainly.api.model.common.attribute.v2.content.data;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.MimeType;

import java.util.Objects;

public class FileAttributeContentData {

    @Schema(description = "File content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Name of the file", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;

    @Schema(description = "Type of the file uploaded", requiredMode = Schema.RequiredMode.REQUIRED)
    private MimeType mimeType;

    public FileAttributeContentData() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileAttributeContentData)) return false;
        FileAttributeContentData that = (FileAttributeContentData) o;
        return Objects.equals(content, that.content) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, fileName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("content", content)
                .append("fileName", fileName)
                .append("mimeType", mimeType)
                .toString();
    }
}
