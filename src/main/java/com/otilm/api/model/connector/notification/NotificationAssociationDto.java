package com.otilm.api.model.connector.notification;

import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Reference to an object associated with the notification subject")
public class NotificationAssociationDto extends NameAndUuidDto {

    @Schema(description = "Resource type of the referenced object", requiredMode = Schema.RequiredMode.REQUIRED)
    private Resource resource;
}
