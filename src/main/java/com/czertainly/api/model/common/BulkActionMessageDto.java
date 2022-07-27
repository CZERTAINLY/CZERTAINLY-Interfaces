package com.czertainly.api.model.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class BulkActionMessageDto extends NameAndUuidDto {

    @Schema(description = "Message describing the associations of the Objects which is preventing the delete operation",
            example = "Object is associated with other items",
            required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BulkActionMessageDto() {
    }

    public BulkActionMessageDto(String uuid, String name, String message) {
        super(uuid, name);
        this.message = message;
    }
}
