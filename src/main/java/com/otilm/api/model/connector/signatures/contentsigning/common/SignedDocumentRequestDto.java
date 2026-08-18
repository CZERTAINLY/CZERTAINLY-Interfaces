package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request carrying a signed document, shared by every operation after the signature value has been embedded.
 *
 * <p>
 * The embed operations return the next signed document, raising the signature a level; the imprint operations return an
 * imprint to be timestamped and advance nothing.
 * </p>
 */
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "SignedDocumentRequest", description = "Request carrying the signed document an operation is to act on")
public class SignedDocumentRequestDto extends ContentSigningFormattingRequestDto {

    @ToString.Exclude
    @NotNull(message = "signedDocument is required")
    @Schema(description = "The signed document as it stands, base64-encoded in JSON. A connector that cannot find "
            + "the signature it is asked to act on answers 422 with errorCode SIGNATURE_NOT_FOUND.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private byte[] signedDocument;

    @Valid
    @Schema(description = "The originally signed content, for detached packaging where the signature does not "
            + "envelop what it signs and the operation cannot proceed without it. Absent for enveloped packaging, "
            + "where the signed document already carries its content.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private DocumentTransferDto detachedContent;
}
