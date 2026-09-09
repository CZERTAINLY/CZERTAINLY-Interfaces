package com.otilm.api.model.client.cryptography.key;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.core.logging.Sensitive;
import com.otilm.api.model.core.secret.Passphrase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Shared body of an operation that hands key material to a caller.
 *
 * <p>
 * Both operations that do this are POSTs with a body rather than reads with query parameters, because a passphrase in a
 * URL is recorded by proxies, browser history and access logs. Each operation publishes its own schema so either can
 * gain an option without changing the other.
 * </p>
 */
@Getter
@Setter
@ToString
public abstract class ExportPassphraseRequestDto {

    /** Shortest passphrase accepted. The result is a file that leaves the platform, so it stands on its own. */
    public static final int MINIMUM_PASSPHRASE_LENGTH = 12;

    @ToString.Exclude
    @Sensitive
    @Schema(description = "Passphrase that will protect the file. At least " + MINIMUM_PASSPHRASE_LENGTH
            + " characters, counted as code points, in Unicode normalization form C: the file leaves the platform and "
            + "its protection is only as good as this value.", requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = MINIMUM_PASSPHRASE_LENGTH)
    @NotNull(message = "passphrase is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Passphrase passphrase;

    @Schema(description = "Attributes required by the provider to export a key, from the export attribute schema",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> exportAttributes;

    /**
     * Checked here rather than with a size constraint because the passphrase is not a string: the value must never be
     * held in one, and a violation message must never quote it.
     *
     * @return whether the passphrase is long enough to protect a file that leaves the platform
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "passphrase must contain at least " + MINIMUM_PASSPHRASE_LENGTH
            + " characters and must not be only whitespace")
    public boolean isPassphraseAcceptable() {
        return passphrase == null
                || (!passphrase.isBlank() && passphrase.codePointLength() >= MINIMUM_PASSPHRASE_LENGTH);
    }

    /**
     * The passphrase is encoded in normalization form C when it protects the file, so a value in another form would be
     * shorter, or simply different, by the time it is used. Requiring the form the check saw keeps them the same.
     *
     * @return whether the passphrase is already in Unicode normalization form C
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "passphrase must be in Unicode normalization form C")
    public boolean isPassphraseNormalized() {
        return passphrase == null || passphrase.isNormalized();
    }
}
