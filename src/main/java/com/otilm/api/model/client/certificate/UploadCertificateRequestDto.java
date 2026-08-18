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

    @Schema(description = "Identify-operation dynamic attributes, as listed by "
            + "GET /v2/operations/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/identify for the "
            + "certificate's RA profile (when this DTO is used as the body of the v1 upload endpoints, the web-API "
            + "listing under /v1/authorities/... is the equivalent). Consumed when the uploaded certificate is "
            + "manually issued, which identifies it at the authority. Optional — an authority that offers no "
            + "identify schema needs none.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ToString.Exclude
    private List<RequestAttribute> identifyAttributes;
}
