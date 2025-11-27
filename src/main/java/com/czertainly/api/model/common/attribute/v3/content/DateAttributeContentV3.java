package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Objects;

@Schema(
        description = "Date attribute content in predefined format",
        type = "object")
public class DateAttributeContentV3 extends BaseAttributeContentV3<LocalDate> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Schema(description = "Date attribute value in format yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate data;

    public DateAttributeContentV3() {
        setContentType(AttributeContentType.DATE);
    }

    public DateAttributeContentV3(LocalDate data) {
        this.data = data;
        setContentType(AttributeContentType.DATE);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateAttributeContentV3)) return false;
        if (!super.equals(o)) return false;
        DateAttributeContentV3 that = (DateAttributeContentV3) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
