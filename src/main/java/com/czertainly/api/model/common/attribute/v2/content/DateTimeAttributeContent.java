package com.czertainly.api.model.common.attribute.v2.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.Objects;

public class DateTimeAttributeContent extends BaseAttributeContent<ZonedDateTime> {

    // ISO_OFFSET_DATE_TIME	Date Time with Offset	2011-12-03T10:15:30+01:00'
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @Schema(description = "DateTime attribute value in format yyyy-MM-ddTHH:mm:ss.SSSXXX", requiredMode = Schema.RequiredMode.REQUIRED)
    private ZonedDateTime data;

    public DateTimeAttributeContent(ZonedDateTime data) {
        this.data = data;
    }

    public DateTimeAttributeContent(String reference, ZonedDateTime data) {
        super(reference, data);
        this.data = data;
    }

    public DateTimeAttributeContent() {
    }

    @Override
    public ZonedDateTime getData() {
        return data;
    }

    @Override
    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateTimeAttributeContent)) return false;
        if (!super.equals(o)) return false;
        DateTimeAttributeContent that = (DateTimeAttributeContent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
