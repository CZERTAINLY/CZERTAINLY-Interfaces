package com.czertainly.api.model.connector;

import io.swagger.v3.oas.annotations.media.Schema;

public class ForceDeleteMessageDto {

    @Schema(description = "UUID of the connector",
            example = "204a57f6-2ed3-45b6-bf09-af8b8c900e33",
            implementation = String.class,
            required = true)
    private String uuid;
    @Schema(description = "Name of the connector",
            implementation = String.class,
            example = "connector1",
            required = true)
    private String name;
    @Schema(description = "Message describing the associations of the connector which is preventing the delete operation",
            implementation = String.class,
            required = true)
    private String message;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
