package com.otilm.api.model.connector.signatures.contentsigning.xades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * XAdES variant of the {@code computeDtbs} request.
 *
 * <p>
 * The family's own request surface — enveloped, enveloping or detached packaging, the ASiC-S and ASiC-E container
 * option, canonicalization and transforms — arrives with the XAdES family work.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(title = "XadesComputeDtbsRequestDto", description = "computeDtbs request for the XAdES family")
public class XadesComputeDtbsRequestDto extends ComputeDtbsRequestDto {

    public XadesComputeDtbsRequestDto() {
        super(SignatureFamily.XADES);
    }
}
