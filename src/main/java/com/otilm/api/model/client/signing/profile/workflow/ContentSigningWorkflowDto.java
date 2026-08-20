package com.otilm.api.model.client.signing.profile.workflow;

import com.otilm.api.model.client.attribute.ResponseAttribute;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.TimestampSourceDto;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Content signing workflow configuration embedded in a {@code SigningProfileDto}.
 *
 * <p>
 * The Signature Formatting Provider and signature fields are null when delegated signing is used.
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ContentSigningWorkflowDto",
        description = "Content signing workflow configuration. The validation material that LONG_TERM and ARCHIVAL "
                + "embed is fetched by the Signature Formatting Provider and is not configured here.")
@ToString(callSuper = true)
public class ContentSigningWorkflowDto extends WorkflowDto {

    @Schema(description = "Signature Formatting Provider that constructs the data-to-be-signed (DTBS) for Content signing. "
            + "Present only when ILM-managed signing is used; null for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NameAndUuidDto signatureFormattingConnector;

    @Schema(description = "Attributes configured on the Signature Formatting Provider that control DTBS construction "
            + "for the content signing workflow. " + "Applicable only when ILM-managed signing is used.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> signatureFormattingConnectorAttributes = new ArrayList<>();

    @Schema(description = "Signature family this profile produces. "
            + "Present only when ILM-managed signing is used; null for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SignatureFamily family;

    @Schema(description = "Highest signature level a request may ask for. A request asking for more is rejected. "
            + "Present only when ILM-managed signing is used; null for delegated signing.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private SignatureLevel maxLevel;

    @Schema(description = "Source of the timestamps embedded at level TIMESTAMPED and above. "
            + "Set when maxLevel is TIMESTAMPED or higher.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private TimestampSourceDto timestampSource;

    @Schema(description = "Largest document accepted for signing, in bytes. Enforced when a signing request arrives, "
            + "under both ILM-managed and delegated signing. Null when no profile-level cap applies.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "5242880")
    private Long documentSizeCap;

    public ContentSigningWorkflowDto() {
        super(SigningWorkflowType.CONTENT_SIGNING);
    }
}
