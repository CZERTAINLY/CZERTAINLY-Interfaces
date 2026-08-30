package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.otilm.api.model.core.connector.v2.ConnectorInterfaceDto;
import com.otilm.api.model.core.discovery.DiscoveryStatus;
import com.otilm.api.model.core.search.AttributeProjectable;
import com.otilm.api.model.core.search.FilterFieldSource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiscoveryListDto extends NameAndUuidDto implements AttributeProjectable {

    @Schema(description = "Discovery Kind", examples = {"IP-HostName"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private DiscoveryStatus status;

    @Schema(description = "Date and time when Discovery started", nullable = true)
    private OffsetDateTime startTime;

    @Schema(description = "Date and time when Discovery finished", nullable = true)
    private OffsetDateTime endTime;

    @Schema(description = "Number of certificates that are discovered", defaultValue = "0")
    private Integer totalCertificatesDiscovered;

    @Schema(description = "UUID of the Discovery Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;

    @Schema(description = "Name of the Discovery Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorName;

    /**
     * The connector interface this run is driven through, and so which generation drives it. Absent for a run against a
     * legacy v1 connector, which declares no connector interface.
     *
     * <p>
     * The prose lives here and not in {@code @Schema}: ConnectorInterfaceDto is a shared component, and OpenAPI 3.0
     * cannot carry a description beside a {@code $ref} — swagger-core hoists a referencing field's description onto the
     * referenced component, rewriting it for every other API that references it.
     */
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ConnectorInterfaceDto connectorInterface;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = AttributeProjectable.ATTRIBUTE_VALUES_DESCRIPTION,
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<FilterFieldSource, Map<String, List<BaseAttributeContentV3<?>>>> attributeValues;
}
