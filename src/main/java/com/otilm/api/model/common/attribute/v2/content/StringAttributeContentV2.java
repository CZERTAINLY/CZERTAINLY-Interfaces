package com.otilm.api.model.common.attribute.v2.content;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "String attribute content", type = "object")
public class StringAttributeContentV2 extends BaseAttributeContentV2<String> {

    @Schema(description = "String attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    public StringAttributeContentV2() {
    }

    public StringAttributeContentV2(String data) {
        super(data);
        this.data = data;
    }

    public StringAttributeContentV2(String reference, String data) {
        super(reference);
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringAttributeContentV2)) {
            return false;
        }
        StringAttributeContentV2 that = (StringAttributeContentV2) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
