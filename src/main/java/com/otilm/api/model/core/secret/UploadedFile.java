package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Objects;

/**
 * The content of a file a caller uploaded in a request body.
 *
 * <p>
 * The file may carry key material, so the bytes are held in a value that is never rendered, never echoed in an error
 * and can be overwritten once used. It is write-only on the wire: a request carries it, a response never does. As with
 * {@link Passphrase}, {@link #clear()} overwrites what this object holds, not every copy the JSON parser may have made.
 * </p>
 */
@JsonDeserialize(using = UploadedFileDeserializer.class)
@Schema(name = "UploadedFile",
        description = "Content of an uploaded file, base64-encoded. Sent in request bodies only.", type = "string",
        format = "byte", accessMode = Schema.AccessMode.WRITE_ONLY, maxLength = UploadedFile.MAXIMUM_ENCODED_LENGTH)
public final class UploadedFile {

    /** Longest file accepted, in decoded bytes. */
    public static final int MAXIMUM_LENGTH = 5 * 1024 * 1024;

    /** The same limit expressed on the base64 text, which is what a schema constraint can measure. */
    public static final int MAXIMUM_ENCODED_LENGTH = (MAXIMUM_LENGTH + 2) / 3 * 4;

    private byte[] content;

    public UploadedFile(byte[] content) {
        Objects.requireNonNull(content, "content is required");
        this.content = content.clone();
    }

    /**
     * The file content, as a copy the caller may overwrite.
     *
     * @return a copy of the bytes
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public byte[] content() {
        return content.clone();
    }

    /**
     * @return the number of bytes the file holds
     */
    public int length() {
        return content.length;
    }

    /**
     * Overwrites the content. Call this once the file has been used; every later read sees an empty file.
     */
    public void clear() {
        Arrays.fill(content, (byte) 0);
        content = new byte[0];
    }

    @Override
    public String toString() {
        return "UploadedFile[" + content.length + " bytes]";
    }
}
