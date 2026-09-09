package com.otilm.api.model.core.secret;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Objects;

/**
 * A passphrase a caller supplies in a request body.
 *
 * <p>
 * The value is held as characters rather than a {@link String} so that the copy this object owns can be overwritten
 * once it has been used, and so no accidental rendering of a surrounding object can print it. It is write-only on the
 * wire: a request carries it, a response never does.
 * </p>
 *
 * <p>
 * What this does not promise: the request body passed through the JSON parser before reaching here, and the platform
 * cannot reach into the parser's buffers. {@link #clear()} overwrites what this object holds, not every copy that may
 * exist in the process. It reduces how long the value is reachable; it is not a guarantee that no copy remains.
 * </p>
 */
@JsonDeserialize(using = PassphraseDeserializer.class)
@Schema(name = "Passphrase", description = "Passphrase supplied by the caller. Sent in request bodies only.",
        type = "string", format = "password", accessMode = Schema.AccessMode.WRITE_ONLY)
public final class Passphrase {

    private static final String REDACTED = "Passphrase[redacted]";

    private char[] characters;

    public Passphrase(char[] characters) {
        Objects.requireNonNull(characters, "characters are required");
        this.characters = characters.clone();
    }

    /**
     * The passphrase characters, as a copy the caller may overwrite.
     *
     * @return a copy of the characters
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public char[] characters() {
        return characters.clone();
    }

    /**
     * How long the passphrase is, in code points of the value as it arrived, which is what the published
     * {@code minLength} constraint counts. The value is not normalized first, because normalizing would copy it into
     * storage {@link #clear()} cannot overwrite; {@link #isNormalized()} refuses a value in another form instead.
     *
     * @return the number of code points in the passphrase
     */
    public int codePointLength() {
        return Character.codePointCount(characters, 0, characters.length);
    }

    /**
     * Whether the passphrase is in Unicode normalization form C, checked in place without copying the value. A
     * passphrase that protects a file is encoded in that form, so a value in another form is refused rather than
     * normalized.
     *
     * @return whether the characters are already in normalization form C
     */
    @JsonIgnore
    public boolean isNormalized() {
        return Normalizer.isNormalized(CharBuffer.wrap(characters), Normalizer.Form.NFC);
    }

    /**
     * @return whether the passphrase is empty or only whitespace, which no operation accepts
     */
    @JsonIgnore
    public boolean isBlank() {
        for (char character : characters) {
            if (!Character.isWhitespace(character)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Overwrites the characters. Call this once the passphrase has been used; every later read sees an empty value.
     */
    public void clear() {
        Arrays.fill(characters, '\0');
        characters = new char[0];
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Passphrase passphrase)) {
            return false;
        }
        // Length is not secret and MessageDigest.isEqual is length-sensitive, so compare lengths first and the
        // characters without an early exit.
        if (characters.length != passphrase.characters.length) {
            return false;
        }
        byte[] mine = bytes(characters);
        byte[] theirs = bytes(passphrase.characters);
        try {
            return MessageDigest.isEqual(mine, theirs);
        } finally {
            Arrays.fill(mine, (byte) 0);
            Arrays.fill(theirs, (byte) 0);
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(characters);
    }

    @Override
    public String toString() {
        return REDACTED;
    }

    private static byte[] bytes(char[] characters) {
        byte[] bytes = new byte[characters.length * 2];
        for (int i = 0; i < characters.length; i++) {
            bytes[i * 2] = (byte) (characters[i] >> 8);
            bytes[i * 2 + 1] = (byte) characters[i];
        }
        return bytes;
    }
}
