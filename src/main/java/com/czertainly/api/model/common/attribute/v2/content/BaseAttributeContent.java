package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.client.attribute.BaseAttributeContentDto;
import com.czertainly.api.model.common.attribute.v2.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.ContentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "contentType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BooleanAttributeContent.class, name = AttributeContentType.Codes.BOOLEAN),
        @JsonSubTypes.Type(value = CodeBlockAttributeContent.class, name = AttributeContentType.Codes.CODEBLOCK),
        @JsonSubTypes.Type(value = CredentialAttributeContent.class, name = AttributeContentType.Codes.CREDENTIAL),
        @JsonSubTypes.Type(value = DateAttributeContent.class, name = AttributeContentType.Codes.DATE),
        @JsonSubTypes.Type(value = DateTimeAttributeContent.class, name = AttributeContentType.Codes.DATETIME),
        @JsonSubTypes.Type(value = FileAttributeContent.class, name = AttributeContentType.Codes.FILE),
        @JsonSubTypes.Type(value = FloatAttributeContent.class, name = AttributeContentType.Codes.FLOAT),
        @JsonSubTypes.Type(value = IntegerAttributeContent.class, name = AttributeContentType.Codes.INTEGER),
        @JsonSubTypes.Type(value = ObjectAttributeContent.class, name = AttributeContentType.Codes.OBJECT),
        @JsonSubTypes.Type(value = SecretAttributeContent.class, name = AttributeContentType.Codes.SECRET),
        @JsonSubTypes.Type(value = StringAttributeContent.class, name = AttributeContentType.Codes.STRING),
        @JsonSubTypes.Type(value = TextAttributeContent.class, name = AttributeContentType.Codes.TEXT),
        @JsonSubTypes.Type(value = TimeAttributeContent.class, name = AttributeContentType.Codes.TIME)
})
@JsonInclude(JsonInclude.Include.ALWAYS)
@Schema(implementation = BaseAttributeContentDto.class)
public class BaseAttributeContent<T> extends AttributeContent implements BaseAttributeContentDto {

    private String reference;

    @Hidden
    @Schema(description = "Content Data", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;

    private AttributeContentType contentType;

    public BaseAttributeContent() {
    }

    public BaseAttributeContent(String reference) {
        this.reference = reference;
    }

    public BaseAttributeContent(String reference, T data) {
        this.reference = reference;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof BaseAttributeContent<?> that)) return false;

        // content is considered equal when reference and data are equal
        return Objects.equals(this.reference, that.getReference()) && Objects.equals(this.getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, data);
    }
}
