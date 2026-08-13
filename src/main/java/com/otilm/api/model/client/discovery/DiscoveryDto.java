package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Setter
@Getter
public class DiscoveryDto {
    @Schema(description = "Discovery name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "List of Attributes for Discovery", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> attributes;
    @Schema(description = "List of Custom Attributes")
    private List<RequestAttribute> customAttributes;
    @Schema(description = "Discovery Provider UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String connectorUuid;
    @Schema(description = "Discovery Kind", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;
    @Schema(description = "List of triggers to be triggered after the discovery is finished, triggers will be evaluated in given order")
    private List<UUID> triggers;

    // minItems documents what Core enforces at the service boundary; deliberately not a jakarta constraint,
    // which would be inert here (no @Valid) and must never fire on an omitted field.
    @ArraySchema(minItems = 1,
            arraySchema = @Schema(
                    description = "Resource types this run should discover, as resource wire codes (e.g. "
                            + "\"certificates\", \"keys\"). Omit for exactly today's v1 semantics: the run "
                            + "discovers certificates only, whether the selected connector implements the v1 or "
                            + "the v2 discovery interface. Supplying it requires a connector implementing "
                            + "discovery v2; a v1 connector rejects the request.",
                    requiredMode = Schema.RequiredMode.NOT_REQUIRED))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Resource> resources;

    @Schema(description = "Per-resource attributes, keyed by resource wire code, collected against the "
            + "per-resource attribute definitions the connector advertises. Omit for exactly "
            + "today's v1 semantics: only the run-level attributes above are sent to the "
            + "connector. A key absent from this map means that resource is discovered with no "
            + "refinement of its own, not that it is excluded — resources alone decides what a run targets.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, propertyNames = Resource.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<Resource, List<RequestAttribute>> resourceAttributes;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("attributes", attributes)
                .append("customAttributes", customAttributes)
                .append("connectorUuid", connectorUuid)
                .append("kind", kind)
                .append("resources", resources)
                // resourceAttributes is deliberately not appended: it multiplies the attribute payload
                // by the number of targeted resources, and the run-level attributes already above give
                // a log reader the shape of what was submitted.
                .toString();
    }
}
