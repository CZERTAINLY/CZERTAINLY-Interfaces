package com.otilm.api.model.connector.signatures.contentsigning.jades;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.common.ComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * JAdES variant of the {@code computeDtbs} request.
 *
 * <p>
 * The family's own request surface — compact or JSON serialization, and detached signing through the sigD mechanism —
 * arrives with the JAdES family work.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(title = "JadesComputeDtbsRequestDto", description = "computeDtbs request for the JAdES family")
public class JadesComputeDtbsRequestDto extends ComputeDtbsRequestDto {

    public JadesComputeDtbsRequestDto() {
        super(SignatureFamily.JADES);
    }
}
