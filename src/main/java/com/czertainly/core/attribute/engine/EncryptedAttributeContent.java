package com.czertainly.core.attribute.engine;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class EncryptedAttributeContent extends BaseAttributeContentV3<String> {

    private String data;

    private String reference;

    private AttributeContentType contentType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedAttributeContent that = (EncryptedAttributeContent) o;
        return Objects.equals(data, that.data) &&
                Objects.equals(reference, that.reference) &&
                contentType == that.contentType;
    }

    @Override
        public int hashCode() {
            return Objects.hash(data, reference, contentType);
        }

}
