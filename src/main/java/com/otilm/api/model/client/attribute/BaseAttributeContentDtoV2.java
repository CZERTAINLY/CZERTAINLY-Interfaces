package com.otilm.api.model.client.attribute;

import com.otilm.api.model.common.attribute.v2.content.BooleanAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.CodeBlockAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.CredentialAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.DateAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.DateTimeAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.FileAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.FloatAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.IntegerAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.ObjectAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.SecretAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.TextAttributeContentV2;
import com.otilm.api.model.common.attribute.v2.content.TimeAttributeContentV2;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Base Attribute ContentV2 definition", type = "object",
        oneOf = {
                BooleanAttributeContentV2.class,
                CodeBlockAttributeContentV2.class,
                CredentialAttributeContentV2.class,
                DateAttributeContentV2.class,
                DateTimeAttributeContentV2.class,
                FileAttributeContentV2.class,
                FloatAttributeContentV2.class,
                IntegerAttributeContentV2.class,
                ObjectAttributeContentV2.class,
                SecretAttributeContentV2.class,
                StringAttributeContentV2.class,
                TextAttributeContentV2.class,
                TimeAttributeContentV2.class})
public interface BaseAttributeContentDtoV2 {

    @Schema(description = "ContentV2 Reference")
    String getReference();

}
