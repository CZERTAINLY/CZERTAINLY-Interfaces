package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.commons.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ForceDeleteMessageDto extends NameAndUuidDto {

    @Schema(description = "Message describing the associations of the Connector which is preventing the delete operation",
            required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
