package com.otilm.api.model.connector.signatures.formatting;

import com.otilm.api.model.client.signing.profile.workflow.SigningWorkflowType;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * OpenAPI schema interface for the polymorphic {@link FormatDtbsRequestDto} hierarchy.
 *
 * <p>
 * The concrete subtype is determined by the {@code type} discriminator ({@link SigningWorkflowType}).
 * </p>
 *
 * <p>
 * {@link SigningWorkflowType#TIMESTAMPING} is the only workflow this union carries. {@code RAW_SIGNING} invokes no
 * formatting provider at all, and {@code CONTENT_SIGNING} is served by the Content Signing Formatting contract instead.
 * </p>
 */
@Schema(name = "FormatDtbsInterface", description = "DTBS formatting request specific to the signing workflow type",
        type = "object", discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = SigningWorkflowType.Codes.TIMESTAMPING,
                        schema = TimestampingFormatDtbsRequestDto.class)},
        oneOf = {TimestampingFormatDtbsRequestDto.class,})
public interface FormatDtbsInterface extends Serializable {

    @Schema(description = "Signing workflow type", requiredMode = Schema.RequiredMode.REQUIRED,
            examples = {SigningWorkflowType.Codes.TIMESTAMPING})
    SigningWorkflowType getType();
}
