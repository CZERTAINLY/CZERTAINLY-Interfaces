package com.czertainly.api.model.core.oid;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RdnAttributeTypeOidPropertiesDto.class, name = OidCategory.Codes.RDN_ATTRIBUTE_TYPE)
})
public interface AdditionalOidPropertiesDto extends Serializable {
}
