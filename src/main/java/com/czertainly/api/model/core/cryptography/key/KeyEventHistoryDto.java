package com.czertainly.api.model.core.cryptography.key;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class KeyEventHistoryDto {
    @Schema(description = "UUID of the event", required = true)
    private String uuid;

    @Schema(description = "UUID of the Key", required = true)
    private String keyUuid;

    @Schema(description = "Event creation time", required = true)
    private LocalDateTime created;

    @Schema(description = "Created By", required = true)
    private String createdBy;

    @Schema(description = "Event type", required = true)
    private KeyEvent event;

    @Schema(description = "Event result", required = true)
    private KeyEventStatus status;

    @Schema(description = "Event message", required = true)
    private String message;

    @Schema(description = "Additional information for the event")
    private HashMap<String, Object> additionalInformation;
}
