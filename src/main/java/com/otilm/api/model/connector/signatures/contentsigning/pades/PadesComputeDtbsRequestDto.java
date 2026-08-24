package com.otilm.api.model.connector.signatures.contentsigning.pades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PAdES variant of the {@code computeDtbs} request.
 *
 * <p>
 * PAdES is always enveloped, so there is no packaging choice to express. The family's own request surface — per
 * signature visible-signature placement, reason and location — arrives with the PAdES family work.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(title = "PadesComputeDtbsRequestDto", description = "computeDtbs request for the PAdES family")
public class PadesComputeDtbsRequestDto extends ComputeDtbsRequestDto {

    public PadesComputeDtbsRequestDto() {
        super(SignatureFamily.PADES);
    }
}
