package com.czertainly.api.model.common.attribute.content;

public class BaseAttributeContent<T> {

    private T value;

    public BaseAttributeContent() { }

    public BaseAttributeContent(T content) {
        this.value = content;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
