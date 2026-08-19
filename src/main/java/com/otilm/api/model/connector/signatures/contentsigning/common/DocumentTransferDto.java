package com.otilm.api.model.connector.signatures.contentsigning.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * How a document reaches the formatting connector: inline, or as a digest only.
 *
 * <p>
 * Enveloped formats (PAdES, enveloped XAdES, ASiC) send the document itself. Detached formats send only its digest, so
 * the customer document never enters the platform at all.
 * </p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "transferMode")
@JsonSubTypes({
        @Type(value = InlineDocumentTransferDto.class, name = DocumentTransferMode.Codes.INLINE),
        @Type(value = DigestOnlyDocumentTransferDto.class, name = DocumentTransferMode.Codes.DIGEST_ONLY)})
@Schema(name = "DocumentTransfer", description = "Document transport for a formatting operation. The required "
        + "transferMode property is the discriminator selecting the transport: an enveloped format sends the "
        + "document inline, a detached format sends only its digest. An inline document is bounded by the size cap "
        + "configured on the Signing Profile, which always stays below the platform's 16 MiB request ceiling.",
        type = "object", discriminatorProperty = "transferMode",
        discriminatorMapping = {
                @DiscriminatorMapping(value = DocumentTransferMode.Codes.INLINE,
                        schema = InlineDocumentTransferDto.class),
                @DiscriminatorMapping(value = DocumentTransferMode.Codes.DIGEST_ONLY,
                        schema = DigestOnlyDocumentTransferDto.class)},
        oneOf = {InlineDocumentTransferDto.class, DigestOnlyDocumentTransferDto.class})
public sealed interface DocumentTransferDto permits InlineDocumentTransferDto, DigestOnlyDocumentTransferDto {

    @Schema(description = "Transport this document uses, and the discriminator selecting the fields that accompany "
            + "it", requiredMode = Schema.RequiredMode.REQUIRED, examples = {DocumentTransferMode.Codes.INLINE})
    DocumentTransferMode getTransferMode();
}
