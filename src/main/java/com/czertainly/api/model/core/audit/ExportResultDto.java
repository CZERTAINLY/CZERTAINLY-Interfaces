package com.czertainly.api.model.core.audit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExportResultDto {
	
	@Schema(
            description = "Name of the file",
            required = true
    )
    private String fileName;
	
	@Schema(
            description = "File content in byte array",
            required = true
    )
    private byte[] fileContent;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fileName", fileName)
                .append("fileContent", fileContent)
                .toString();
    }
}
