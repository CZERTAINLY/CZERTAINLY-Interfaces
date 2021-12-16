package com.czertainly.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class UuidDto {

    @Schema(description = "Object UUID",
            required = true)
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UuidDto() {
    }
}
