package com.czertainly.api.model.common.attribute.constraint.data;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

public class DateTimeAttributeConstraintData {
    @Schema(description = "Start of the datetime for validation")
    private LocalDateTime from;

    @Schema(description = "End of the datetime for validation")
    private LocalDateTime to;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from", from)
                .append("to", to)
                .toString();
    }
}
