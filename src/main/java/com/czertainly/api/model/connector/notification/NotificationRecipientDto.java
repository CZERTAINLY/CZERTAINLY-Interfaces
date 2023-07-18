
package com.czertainly.api.model.connector.notification;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class NotificationRecipientDto {
    @Schema(description = "Recipient name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Recipient email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Schema(description = "Mapping attributes value for recipient", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> mappingAttributes;

    @Schema(description = "List of Custom Attributes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttributeDto> customAttributes;
}
