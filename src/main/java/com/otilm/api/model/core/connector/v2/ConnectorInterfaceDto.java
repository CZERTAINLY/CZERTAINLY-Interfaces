package com.otilm.api.model.core.connector.v2;

import com.otilm.api.model.client.connector.v2.ConnectorInterface;
import com.otilm.api.model.client.connector.v2.FeatureFlag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;

// The description belongs on the type, not on the fields that reference it. This is a shared component reached
// through a $ref from several resources, and OpenAPI 3.0 cannot carry a description beside a $ref, so a description
// on any referencing field is hoisted onto this component instead — whichever resource resolves last wins. A
// class-level one is the only form that hoisting cannot overwrite.
@Schema(description = "An interface a connector implements: its code, its version, and the features it supports. "
        + "The version decides which generation of the provider contract Core speaks to that connector.")
@Data
public class ConnectorInterfaceDto implements Serializable {

    @Schema(description = "UUID of the connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID uuid;

    @Schema(description = "Code of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private ConnectorInterface code;

    @Schema(description = "Version of the implemented connector interface", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Schema(description = "Features supported by the connector interface",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<FeatureFlag> features;

}
