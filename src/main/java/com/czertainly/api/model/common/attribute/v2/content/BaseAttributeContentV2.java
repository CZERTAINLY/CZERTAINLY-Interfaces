package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.client.attribute.BaseAttributeContentDtoV2;
import com.czertainly.api.model.common.attribute.common.BaseAttributeContent;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Schema(implementation = BaseAttributeContentDtoV2.class)
public class BaseAttributeContentV2<T> extends BaseAttributeContent implements BaseAttributeContentDtoV2 {

    private String reference;

    @Hidden
    @Schema(description = "Content Data", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;

    public BaseAttributeContentV2() {
    }

    public BaseAttributeContentV2(String reference) {
        this.reference = reference;
    }

    public BaseAttributeContentV2(String reference, T data) {
        this.reference = reference;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof BaseAttributeContentV2<?> that)) return false;

        // content is considered equal when reference and data are equal
        return Objects.equals(this.reference, that.getReference()) && Objects.equals(this.getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, data);
    }

    @Override
    public int getVersion() {
        return 2;
    }
}
