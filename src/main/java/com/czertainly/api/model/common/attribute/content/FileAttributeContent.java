package com.czertainly.api.model.common.attribute.content;

public class FileAttributeContent extends BaseAttributeContent<String> {

    private String fileName;
    private String contentType;

    public FileAttributeContent() { }

    public FileAttributeContent(String value, String fileName, String contentType) {
        super(value);
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
