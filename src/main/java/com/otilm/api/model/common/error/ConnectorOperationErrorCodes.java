package com.otilm.api.model.common.error;

/**
 * Shared classification of connector error codes whose meaning is the same across provider
 * interfaces.
 *
 * <p>Lives beside {@link ErrorCode} so every consumer classifies a code the same way, and a newly
 * introduced code is recognised everywhere at once.
 */
public final class ConnectorOperationErrorCodes {

    private ConnectorOperationErrorCodes() {
    }

    /**
     * True when the connector reports that it no longer tracks — or never tracked — the operation,
     * meaning the upstream handle is gone.
     *
     * <p>Callers treat this as a soft success for cancel-style operations, since an operation the
     * connector cannot find is already in the terminal state cancel was asking for, and as a
     * terminal failure when polling, since no amount of retrying will make the handle reappear.
     */
    public static boolean isOperationNotTracked(ErrorCode code) {
        return code == ErrorCode.OPERATION_NOT_TRACKED
                || code == ErrorCode.REGISTRATION_NOT_FOUND;
    }
}
