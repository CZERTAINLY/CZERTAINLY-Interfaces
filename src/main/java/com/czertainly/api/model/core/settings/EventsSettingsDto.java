package com.czertainly.api.model.core.settings;

import com.czertainly.api.model.core.other.ResourceEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EventsSettingsDto implements SettingsDto {

    @NotNull
    @Schema(description = "Map of triggers associated to event where key is resource event enum", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<ResourceEvent, List<UUID>> notificationsMapping = new EnumMap<>(ResourceEvent.class);
}
