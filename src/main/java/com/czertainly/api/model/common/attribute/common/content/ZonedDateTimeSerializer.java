package com.czertainly.api.model.common.attribute.common.content;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    private static final DateTimeFormatter WITH_MILLIS_AND_OFFSET =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private static final DateTimeFormatter WITHOUT_MILLIS =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {

        if (value.getNano() == 0 && value.getOffset().equals(ZoneOffset.UTC)) {
            gen.writeString(value.format(WITHOUT_MILLIS));
        } else if (value.getNano() == 0) {
            gen.writeString(value.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));
        } else {
            gen.writeString(value.format(WITH_MILLIS_AND_OFFSET));
        }
    }
}

