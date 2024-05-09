package com.czertainly.api.model.core.other;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class ResourceDto {

    @Schema(description = "Resource code",
            example = "certificates",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = Resource.class)
    private Resource resource;

    @Schema(description = "If resource has Object access permissions. True = Yes, False = No", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean hasObjectAccess;

    @Schema(description = "Support assigning custom attributes to resource objects", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean hasCustomAttributes;

    @Schema(description = "Has events that can be used in triggers", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean hasEvents;

    @Schema(description = "Has rule evaluator that can evaluate conditions and actions", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean hasRuleEvaluator;

    public Boolean hasObjectAccess() {
        return hasObjectAccess;
    }

    public boolean hasCustomAttributes() {
        return hasCustomAttributes;
    }

    public boolean hasEvents() {
        return hasEvents;
    }

    public boolean hasRuleEvaluator() {
        return hasRuleEvaluator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("resource", resource)
                .append("hasObjectAccess", hasObjectAccess)
                .append("hasCustomAttributes", hasCustomAttributes)
                .append("hasEvents", hasEvents)
                .append("hasRuleEvaluator", hasRuleEvaluator)
                .toString();
    }
}
