package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute Content",
        type = "object",
        discriminatorProperty = "contentType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeContentType.Codes.BOOLEAN, schema = BooleanAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.CODEBLOCK, schema = CodeBlockAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.CREDENTIAL, schema = CredentialAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.DATE, schema = DateAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.DATETIME, schema = DateTimeAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.FILE, schema = FileAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.FLOAT, schema = FloatAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.INTEGER, schema = IntegerAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.OBJECT, schema = ObjectAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.SECRET, schema = SecretAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.STRING, schema = StringAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.TEXT, schema = TextAttributeContentV3.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.TIME, schema = TimeAttributeContentV3.class)
        },
        oneOf = {
                BooleanAttributeContentV3.class,
                CodeBlockAttributeContentV3.class,
                CredentialAttributeContentV3.class,
                DateAttributeContentV3.class,
                DateTimeAttributeContentV3.class,
                FileAttributeContentV3.class,
                FloatAttributeContentV3.class,
                IntegerAttributeContentV3.class,
                ObjectAttributeContentV3.class,
                SecretAttributeContentV3.class,
                StringAttributeContentV3.class,
                TextAttributeContentV3.class,
                TimeAttributeContentV3.class
        }
)
public interface BaseAttributeContentDtoV3 {

    @Schema(description = "Content Reference")
    String getReference();

}
