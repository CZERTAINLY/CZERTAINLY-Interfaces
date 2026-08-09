package com.otilm.api.model.connector.notification;

import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "Attribute values prepared for notification templates: plain scalar values or reference strings only, decoupled from internal attribute content versioning")
public class NotificationAttributeDto {

    @Schema(description = "Attribute name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Human-readable attribute label", requiredMode = Schema.RequiredMode.REQUIRED)
    private String label;

    @Schema(description = "Attribute content type", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttributeContentType contentType;

    @Schema(description = "Extracted values; scalar types carry raw data, complex types carry the human-readable reference string", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Object> values;

    @Schema(description = "Metadata only: aggregate set of source objects that contributed any value to this attribute. NOT positionally paired with values — values are deduplicated independently of source accumulation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<NameAndUuidDto> sourceObjects;
}
