package com.otilm.api.model.common.events.data;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InternalNotificationEventData implements EventData {
    @Schema(description = "Notification message", requiredMode = Schema.RequiredMode.REQUIRED)
    private String text;

    @Schema(description = "Notification message detail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String detail;

    @Schema(description = "Type of the object within the target the notification is about, when it is not the target "
            + "itself", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Resource subjectObjectType;

    @Schema(description = "Identification (UUID) of the object within the target the notification is about",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String subjectObjectIdentification;

    @Schema(description = "Identification (UUID) of the object the subject is nested in, when the subject is not "
            + "top-level within the target", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String subjectParentIdentification;

    public InternalNotificationEventData(String text, String detail) {
        this.text = text;
        this.detail = detail;
    }
}
