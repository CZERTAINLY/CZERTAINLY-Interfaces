package com.otilm.api.model.core.secret;

import com.otilm.api.exception.PlatformException;
import java.io.IOException;

/**
 * Raised when a file field carries something other than a base64 JSON string.
 *
 * <p>
 * The message is fixed and never quotes the offending value, which is what makes it safe to forward: a request that
 * puts key material in the wrong place must not have it echoed back in an error.
 * </p>
 */
public class UploadedFileFormatException extends IOException implements PlatformException {

    private static final long serialVersionUID = 1L;

    public UploadedFileFormatException(String message) {
        super(message);
    }
}
