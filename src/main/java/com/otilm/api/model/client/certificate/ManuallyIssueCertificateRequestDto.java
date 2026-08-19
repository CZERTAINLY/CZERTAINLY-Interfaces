package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Body of the manual-issue (finalize) operation: the uploaded certificate plus the identify-operation attribute values
 * the authority asks for when verifying it.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ManuallyIssueCertificateRequestDto extends UploadCertificateRequestDto {

    @Schema(description = "Identify-operation dynamic attributes, as listed by "
            + "GET /v2/operations/authorities/{authorityUuid}/raProfiles/{raProfileUuid}/attributes/identify for the "
            + "certificate's RA profile. Optional — an authority that offers no identify schema needs none.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ToString.Exclude
    private List<RequestAttribute> identifyAttributes;
}
