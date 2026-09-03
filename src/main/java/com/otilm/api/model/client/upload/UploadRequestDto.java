package com.otilm.api.model.client.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.core.logging.Sensitive;
import com.otilm.api.model.core.secret.UploadedFile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Shared body of an operation that takes an uploaded file.
 *
 * <p>
 * The file travels base64-encoded inside the JSON body like every other upload in the platform, and is held as an
 * {@link UploadedFile} because it may carry key material: the value is never rendered, never echoed in an error, and
 * can be overwritten once used. Each operation publishes its own schema so any of them can gain an option without
 * changing the others.
 * </p>
 */
@Getter
@Setter
@ToString
public abstract class UploadRequestDto {

    @ToString.Exclude
    @Sensitive
    @Schema(description = """
            Content of the uploaded file, base64-encoded: a container such as PKCS#12 or PEM, a standalone key, or a
            certificate. At most 5 MiB decoded. The platform holds it in memory only, never writes it anywhere and
            never echoes it in an error, since the file may carry key material.
            """, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "file is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UploadedFile file;

    /**
     * Checked here rather than with a size constraint because the file is not a string, and the violation must name the
     * limit rather than the content.
     *
     * @return whether the file is within the platform's size limit
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "file must not exceed " + UploadedFile.MAXIMUM_LENGTH + " bytes")
    public boolean isFileWithinLimit() {
        return file == null || file.length() <= UploadedFile.MAXIMUM_LENGTH;
    }
}
