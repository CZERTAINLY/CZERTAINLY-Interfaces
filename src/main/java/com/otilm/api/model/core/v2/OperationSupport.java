package com.otilm.api.model.core.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Per-operation support flags for an authority or RA profile.
 *
 * <p>
 * An instance of this class is returned for each operation kind (ISSUE, RENEW, REVOKE, REGISTER) inside
 * {@link AvailableOperationsDto}. Operators use these flags to validate flows and drive UI affordances before invoking
 * them.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OperationSupport", description = "Per-operation support flags for an authority/RA profile.")
public class OperationSupport {

    @Schema(description = "Operation kind (ISSUE, RENEW, REVOKE, REGISTER).", requiredMode = Schema.RequiredMode.REQUIRED)
    private CertificateOperationKind operation;

    @Schema(description = "Whether the operation is supported by this authority.")
    private boolean supported;

    @Schema(description = "Whether the platform provides managed status-polling for this operation's asynchronous "
            + "completion (the authority is v3 and advertises status polling). A connector may still "
            + "complete asynchronously without this — the certificate is then left in a pending state for "
            + "an operator finalize/confirm action.")
    private boolean asyncSupported;

    @Schema(description = "Whether an in-flight asynchronous execution of this operation can be cancelled.")
    private boolean cancelSupported;

}
