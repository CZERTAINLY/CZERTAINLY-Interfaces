package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute content definition",
        type = "object",
        oneOf = {
                BooleanAttributeContent.class,
                CodeBlockAttributeContent.class,
                CredentialAttributeContent.class,
                DateAttributeContent.class,
                DateTimeAttributeContent.class,
                FileAttributeContent.class,
                FloatAttributeContent.class,
                IntegerAttributeContent.class,
                ObjectAttributeContent.class,
                SecretAttributeContent.class,
                StringAttributeContent.class,
                TextAttributeContent.class,
                TimeAttributeContent.class
        }
)
public interface BaseAttributeContentDto {

    @Schema(description = "Content Reference")
    String getReference();

}
