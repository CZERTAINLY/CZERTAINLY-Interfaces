package com.czertainly.api.model.common.attribute.v2.content;

public class BaseAttributeContent<T> extends AttributeContent {

    private String reference;
    private T data;

    public BaseAttributeContent() { }

    public BaseAttributeContent(String reference) {
        this.reference = reference;
    }

    public BaseAttributeContent(String reference, T data) {
        this.reference = reference;
        this.data = data;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
