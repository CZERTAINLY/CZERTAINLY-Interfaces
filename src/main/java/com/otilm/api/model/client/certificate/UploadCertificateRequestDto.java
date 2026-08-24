package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Class representing new certificate upload request
 */
@Data
public class UploadCertificateRequestDto {

    @Schema(description = "Base64 Content of the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    private String certificate;

    @Schema(description = "Custom Attributes for the Certificate", requiredMode = Schema.RequiredMode.REQUIRED)
    @ToString.Exclude
    private List<RequestAttribute> customAttributes;
}
