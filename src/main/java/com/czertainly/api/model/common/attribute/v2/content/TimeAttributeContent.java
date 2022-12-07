package com.czertainly.api.model.common.attribute.v2.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.Objects;

public class TimeAttributeContent extends BaseAttributeContent<LocalTime> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    @Schema(description = "Time attribute value", required = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeAttributeContent)) return false;
        if (!super.equals(o)) return false;
        TimeAttributeContent that = (TimeAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
