package com.czertainly.api.model.common.attribute.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalTime;

public class TimeAttributeContent extends BaseAttributeContent<LocalTime> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime data;

    public TimeAttributeContent() {
    }

    public TimeAttributeContent(LocalTime data) {
        this.data = data;
    }

    @Override
    public LocalTime getData() {
        return data;
    }

    @Override
    public void setData(LocalTime data) {
        this.data = data;
    }
}
