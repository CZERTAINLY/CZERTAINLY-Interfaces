package com.otilm.api.model.connector.signatures.contentsigning.cades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CAdES variant of the {@code computeDtbs} request.
 *
 * <p>
 * The family's own request surface — enveloping or detached packaging and the ASiC-S container option — arrives with
 * the CAdES family work. Detached CAdES is digest-only, so the document itself never enters the platform.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(title = "CadesComputeDtbsRequestDto", description = "computeDtbs request for the CAdES family")
public class CadesComputeDtbsRequestDto extends ComputeDtbsRequestDto {

    public CadesComputeDtbsRequestDto() {
        super(SignatureFamily.CADES);
    }
}
