package com.otilm.api.model.connector.v3.certificate;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.connector.v3.AuthorityV3ScopedRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CertificateIdentificationRequestDtoV3 extends AuthorityV3ScopedRequestDto {

    @Schema(description = "Base64 of cert to identify at the upstream CA", format = "byte",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "certificate is required for identify")
    private String certificate;

    @Schema(description = "Identify-specific dynamic attributes (schema from /identify/attributes)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ToString.Exclude
    private List<RequestAttribute> attributes;
}
