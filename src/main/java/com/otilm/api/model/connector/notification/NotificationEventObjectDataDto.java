package com.otilm.api.model.connector.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@Schema(description = "Object data enabled on the notification profile, describing the event's subject object. Best-effort: categories the subject does not support, that are disabled, or that failed to load are absent")
public class NotificationEventObjectDataDto {

    @Schema(description = "The object this data describes: the event object, or for approval events the approval's target object", requiredMode = Schema.RequiredMode.REQUIRED)
    private NotificationAssociationDto subject;

    @Schema(description = "The subject's custom attributes keyed by attribute name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, NotificationAttributeDto> customAttributes;

    @Schema(description = "The subject's connector-sourced metadata, grouped by connector and source object type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<NotificationMetadataGroupDto> metadata;

    @Schema(description = "The subject's associated objects: owner, groups, RA profile", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<NotificationAssociationDto> associations;

    @Schema(description = "The subject's content representation when its resource type provides one", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NotificationObjectContentDto content;
}
