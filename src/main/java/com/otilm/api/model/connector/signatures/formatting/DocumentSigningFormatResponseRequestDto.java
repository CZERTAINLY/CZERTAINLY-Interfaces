package com.otilm.api.model.connector.signatures.formatting;

import com.otilm.api.model.client.signing.profile.workflow.SigningWorkflowType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Response formatting request for the Document Signing workflow.
 *
 * <p>
 * No additional fields beyond the base at this time. Reserved for future document-signing-specific properties.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(title = "DocumentSigningFormatResponseRequestDto", description = "Response formatting request for Document Signing")
public class DocumentSigningFormatResponseRequestDto extends FormatResponseRequestDto {

    public DocumentSigningFormatResponseRequestDto() {
        super(SigningWorkflowType.DOCUMENT_SIGNING);
    }
}
