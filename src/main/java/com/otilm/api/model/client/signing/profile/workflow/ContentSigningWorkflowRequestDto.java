package com.otilm.api.model.client.signing.profile.workflow;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.TimestampSourceRequestDto;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Content signing workflow configuration request embedded in a Signing Profile create/update request.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ContentSigningWorkflowRequestDto",
        description = "Content signing workflow configuration request. The validation material that LONG_TERM and "
                + "ARCHIVAL embed is fetched by the Signature Formatting Provider and needs no configuration here.")
@ToString(callSuper = true)
public class ContentSigningWorkflowRequestDto extends WorkflowRequestDto {

    @Schema(description = "UUID of the Signature Formatting Provider that constructs the data-to-be-signed (DTBS) for Content signing. "
            + "Required for ILM-managed signing; must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID signatureFormattingConnectorUuid;

    @Schema(description = "Attributes for the Signature Formatting Provider that control DTBS construction "
            + "for the content signing workflow. " + "Must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> signatureFormattingConnectorAttributes = new ArrayList<>();

    @Schema(description = "Signature family this profile produces. "
            + "Required for ILM-managed signing; must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SignatureFamily family;

    @Schema(description = "Highest signature level a request may ask for. A request asking for more is rejected. "
            + "Required for ILM-managed signing; must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SignatureLevel maxLevel;

    @Valid
    @Schema(description = "Source of the timestamps embedded at level TIMESTAMPED and above. "
            + "Required when maxLevel is TIMESTAMPED or higher, and must be omitted when maxLevel is SIGNED, "
            + "which embeds no timestamp. Must be omitted for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private TimestampSourceRequestDto timestampSource;

    @Positive
    @Schema(description = "Largest document accepted for signing, in bytes. Enforced when a signing request arrives, "
            + "under both ILM-managed and delegated signing. When omitted, no profile-level cap applies.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5242880")
    private Long documentSizeCap;

    public ContentSigningWorkflowRequestDto() {
        super(SigningWorkflowType.CONTENT_SIGNING);
    }
}
