package com.czertainly.api.model.core.connector.v2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "version", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConnectInfoV1.class, name = "v1"),
        @JsonSubTypes.Type(value = ConnectInfoV2.class, name = "v2")
})
@Schema(implementation = ConnectInfoDto.class)
public abstract class ConnectInfo {

    @Schema(description = "Version of the Connector.",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "v2")
    private String version;

}
