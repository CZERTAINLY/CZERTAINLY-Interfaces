package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ForceDeleteMessageDto extends NameAndUuidDto {

    @Schema(description = "Message describing the associations of the Connector which is preventing the delete operation",
            example = "Object is associated with other items",
            required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
