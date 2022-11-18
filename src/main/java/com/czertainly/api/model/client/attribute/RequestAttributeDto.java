package com.czertainly.api.model.client.attribute;

import com.czertainly.api.model.common.attribute.v2.content.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * This class contains set of properties to represent
 * an Attribute definition provided by the client
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request attribute to send attribute content for object")
public class RequestAttributeDto {

    /**
     * UUID of the Attribute
     **/
    @Schema(
            description = "UUID of the Attribute",
            example = "166b5cf52-63f2-11ec-90d6-0242ac120003"
    )
    private String uuid;

    /**
     * Name of the Attribute
     **/
    @Schema(
            description = "Name of the Attribute",
            example = "Attribute",
            required = true
    )
    private String name;

    /**
     * Content of the Attribute
     **/
    @Schema(
        description = "Content of the Attribute",
        required = true,
        type = "object",
        oneOf = {
            BooleanAttributeContent.class,
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
    private List<BaseAttributeContent> content;

    public RequestAttributeDto() {
        super();
    }

    public RequestAttributeDto(RequestAttributeDto original) {
        this.uuid = original.uuid;
        this.name = original.name;
        this.content = original.content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BaseAttributeContent> getContent() {
        return content;
    }

    public void setContent(List<BaseAttributeContent> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("content", content)
                .toString();
    }
}
