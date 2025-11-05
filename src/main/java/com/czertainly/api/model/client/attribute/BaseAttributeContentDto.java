package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.*;
import com.czertainly.api.model.common.attribute.v2.content.*;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Base Attribute Content",
        type = "object",
        discriminatorProperty = "contentType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = AttributeContentType.Codes.BOOLEAN, schema = BooleanAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.CODEBLOCK, schema = CodeBlockAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.CREDENTIAL, schema = CredentialAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.DATE, schema = DateAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.DATETIME, schema = DateTimeAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.FILE, schema = FileAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.FLOAT, schema = FloatAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.INTEGER, schema = IntegerAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.OBJECT, schema = ObjectAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.SECRET, schema = SecretAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.STRING, schema = StringAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.TEXT, schema = TextAttributeContent.class),
                @DiscriminatorMapping(value = AttributeContentType.Codes.TIME, schema = TimeAttributeContent.class),
                @DiscriminatorMapping(value = AttributeType.Codes.INFO, schema = InfoAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.GROUP, schema = GroupAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.META, schema = MetadataAttribute.class),
                @DiscriminatorMapping(value = AttributeType.Codes.CUSTOM, schema = CustomAttribute.class)
        },
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
