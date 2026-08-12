package com.otilm.api.model.client.signing.profile.workflow;

import com.otilm.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Document signing workflow configuration request embedded in a Signing Profile create/update request.
 *
 * <p>
 * Both fields apply to ILM-managed signing only and must be omitted (or set to null) when the Signing Profile uses
 * delegated signing.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "DocumentSigningWorkflowRequestDto", description = "Document signing workflow configuration request")
@ToString(callSuper = true)
public class DocumentSigningWorkflowRequestDto extends WorkflowRequestDto {

    @Schema(description = "UUID of the Signature Formatting Provider that constructs the data-to-be-signed (DTBS) for Document signing. "
            + "Required for ILM-managed signing; must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID signatureFormattingConnectorUuid;

    @Schema(description = "Attributes for the Signature Formatting Provider that control DTBS construction "
            + "for the document signing workflow. " + "Applicable only when ILM-managed signing is used.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> signatureFormattingConnectorAttributes = new ArrayList<>();

    public DocumentSigningWorkflowRequestDto() {
        super(SigningWorkflowType.DOCUMENT_SIGNING);
    }
}
