package com.otilm.api.model.connector.cryptography.key;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DestroyKeyRequestDto {

    @Schema(description = "List of Token Profile Attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> tokenProfileAttributes;

    @Schema(description = "Attributes of the Key")
    private List<MetadataAttributeV2> keyAttributes;

}
