package com.czertainly.api.model.common;

import com.czertainly.api.model.core.logging.Loggable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UuidDto implements Serializable, Loggable {

    @Schema(description = "Object identifier",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String uuid;

    public UuidDto() {
    }

    public UuidDto(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Serializable toLogData() {
        return new UuidDto(uuid);
    }
}
