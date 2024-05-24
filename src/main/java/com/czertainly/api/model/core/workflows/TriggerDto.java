package com.czertainly.api.model.core.workflows;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import com.czertainly.api.model.core.other.ResourceEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class TriggerDto extends NameAndUuidDto {

    @Schema(
            description = "Description of the trigger"
    )
    private String description;

    @Schema(
            description = "Type of the trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TriggerType type;

    @Schema(
            description = "Resource associated with the trigger",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

    @Schema(
            description = "Flag if to ignore object when trigger rules are matched and do not perform any actions and stop evaluating other triggers. Based on context could have other implications to object processing. If ignore is set, trigger does not have any actions.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean ignoreTrigger;

    @Schema(
            description = "Event that should fire trigger"
    )
    private ResourceEvent event;

    @Schema(
            description = "Resource associated with trigger event"
    )
    private Resource eventResource;

}
