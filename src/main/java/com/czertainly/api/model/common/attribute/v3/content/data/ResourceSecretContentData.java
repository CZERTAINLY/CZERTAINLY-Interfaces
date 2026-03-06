package com.czertainly.api.model.common.attribute.v3.content.data;

import com.czertainly.api.model.connector.secrets.content.SecretContent;
import com.czertainly.api.model.core.auth.AttributeResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "ResourceSecretContentData",
        description = "Content data for resource object attribute containing secret content"
)
public class ResourceSecretContentData extends ResourceObjectContentData {

    @Schema(description = "Secret content of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SecretContent content;

    public ResourceSecretContentData() {
        super(AttributeResource.SECRET);
    }

    public ResourceSecretContentData(String uuid, String name, SecretContent content) {
        this();
        this.uuid = uuid;
        this.name = name;
        this.content = content;
    }

}
