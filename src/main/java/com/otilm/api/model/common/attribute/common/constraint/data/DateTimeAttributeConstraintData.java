package com.otilm.api.model.common.attribute.common.constraint.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class DateTimeAttributeConstraintData implements Serializable {
    @Schema(description = "Start of the datetime for validation")
    private LocalDateTime from;

    @Schema(description = "End of the datetime for validation")
    private LocalDateTime to;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from", from)
                .append("to", to)
                .toString();
    }
}
