package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.connector.signatures.contentsigning.cades.CadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.jades.JadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.pades.PadesComputeDtbsRequestDto;
import com.otilm.api.model.connector.signatures.contentsigning.xades.XadesComputeDtbsRequestDto;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * OpenAPI schema for the polymorphic {@link ComputeDtbsRequestDto} hierarchy.
 *
 * <p>
 * The discriminator is the required {@code family} property on each subtype rather than a wrapper: OpenAPI's
 * {@code discriminator} requires {@code propertyName} to name a property present in every {@code oneOf} subschema, so a
 * wrapper-level discriminator cannot be expressed at all.
 * </p>
 */
@Schema(name = "ComputeDtbsRequest",
        description = "Request to compute the data-to-be-signed bytes. The required family "
                + "property is the discriminator selecting the family-specific request shape.",
        type = "object", discriminatorProperty = "family",
        discriminatorMapping = {
                @DiscriminatorMapping(value = SignatureFamily.Codes.PADES, schema = PadesComputeDtbsRequestDto.class),
                @DiscriminatorMapping(value = SignatureFamily.Codes.XADES, schema = XadesComputeDtbsRequestDto.class),
                @DiscriminatorMapping(value = SignatureFamily.Codes.CADES, schema = CadesComputeDtbsRequestDto.class),
                @DiscriminatorMapping(value = SignatureFamily.Codes.JADES, schema = JadesComputeDtbsRequestDto.class)},
        oneOf = {
                PadesComputeDtbsRequestDto.class,
                XadesComputeDtbsRequestDto.class,
                CadesComputeDtbsRequestDto.class,
                JadesComputeDtbsRequestDto.class})
public interface ComputeDtbsInterface {

    @Schema(description = "Signature family this request targets", requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {SignatureFamily.Codes.CADES})
    SignatureFamily getFamily();
}
