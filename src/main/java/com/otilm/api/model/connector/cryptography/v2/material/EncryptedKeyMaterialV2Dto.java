package com.otilm.api.model.connector.cryptography.v2.material;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import lombok.ToString;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/**
 * Protected key material exchanged between the platform and a cryptography provider. One structure carries every key
 * kind, in the single protection profile {@code encryptedPrivateKeyInfo} describes, so a connector implements one
 * scheme rather than a matrix.
 *
 * @see com.otilm.api.model.client.connector.v2.FeatureFlag#KEY_IMPORT
 * @see com.otilm.api.model.client.connector.v2.FeatureFlag#KEY_EXPORT
 */
@ToString
@Schema(name = "EncryptedKeyMaterialV2Dto",
        description = "Protected key material as a DER-encoded PKCS#8 `EncryptedPrivateKeyInfo`, protected with PBES2 "
                + "using PBKDF2-HMAC-SHA256 and AES-256-CBC. See `encryptedPrivateKeyInfo` for the full profile.",
        additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class EncryptedKeyMaterialV2Dto {

    /** Shortest salt accepted for the key derivation, in bytes. */
    public static final int MINIMUM_SALT_LENGTH = 16;

    /** Only accepted length of the AES-CBC initialisation vector, in bytes. */
    public static final int INITIALISATION_VECTOR_LENGTH = 16;

    /** Only accepted derived-key length when the key derivation states one, in bytes. */
    public static final int DERIVED_KEY_LENGTH = 32;

    /** Fewest key-derivation iterations accepted. */
    public static final int MINIMUM_ITERATIONS = 100_000;

    /** Most key-derivation iterations accepted, bounding the work an unopened envelope can demand. */
    public static final int MAXIMUM_ITERATIONS = 10_000_000;

    /** AES block length in bytes; the ciphertext is a whole number of these. */
    public static final int CIPHER_BLOCK_LENGTH = 16;

    /**
     * Longest DER envelope accepted, in bytes. The largest private key any supported algorithm produces is a few
     * kilobytes, so this leaves generous room for future algorithms while keeping the work an envelope can demand
     * bounded: parsing re-encodes and copies it.
     */
    public static final int MAXIMUM_ENVELOPE_LENGTH = 64 * 1024;

    private static final String MALFORMED_MESSAGE = "encryptedPrivateKeyInfo must contain a DER-encoded PKCS#8 EncryptedPrivateKeyInfo";

    @ToString.Exclude
    private byte[] encryptedPrivateKeyInfo;

    @ToString.Exclude
    private Protection protection;

    private boolean canonical;

    @Schema(description = """
            Base64-encoded DER PKCS#8 `EncryptedPrivateKeyInfo` (RFC 5958). The decrypted plaintext is `PrivateKeyInfo`
            syntax whose algorithm identifier states the key kind.

            Exactly one protection profile is accepted, and it must be readable from the envelope alone:

            - **Scheme** — PBES2 (RFC 8018) with PBKDF2-HMAC-SHA256 as the key derivation, its parameters `NULL` as
              RFC 8018 requires, and AES-256-CBC as the encryption.
            - **Salt** — at least 16 bytes, from a cryptographically secure random source, never reused.
            - **Initialisation vector** — exactly 16 bytes, from a cryptographically secure random source, never reused.
            - **Iterations** — between 100000 and 10000000; 600000 is recommended.
            - **Key length** — either absent, which the encryption scheme already implies, or exactly 32. Absent is the
              common case: OpenSSL does not emit it.
            - **Ciphertext** — a non-empty whole number of 16-byte blocks.
            - **Total length** — at most 65536 bytes of DER.

            Every way an envelope can fall short of this — bytes longer than the maximum, bytes that are not canonical
            DER, and a canonical envelope outside the profile — is refused as a field validation failure on this field
            (`VALIDATION_FAILED`), so a caller is told which rule it broke rather than that its body was unreadable.

            The profile carries no integrity protection of its own, so a successful decrypt is evidence about the
            passphrase, not about who produced the envelope.
            """, type = "string", format = "byte", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "encryptedPrivateKeyInfo is required")
    public byte[] getEncryptedPrivateKeyInfo() {
        return encryptedPrivateKeyInfo == null ? null : encryptedPrivateKeyInfo.clone();
    }

    /**
     * Stores the bytes and reads the protection facts from them. Nothing here refuses a value: every rule is reported
     * as a violation on this field.
     */
    @JsonSetter("encryptedPrivateKeyInfo")
    public void setEncryptedPrivateKeyInfo(byte[] encryptedPrivateKeyInfo) {
        if (encryptedPrivateKeyInfo == null) {
            this.encryptedPrivateKeyInfo = null;
            this.protection = null;
            this.canonical = false;
            return;
        }

        this.encryptedPrivateKeyInfo = encryptedPrivateKeyInfo.clone();
        // Parsing an envelope re-encodes and copies it, so an oversized one is refused before that work is done.
        EncryptedPrivateKeyInfo parsed = this.encryptedPrivateKeyInfo.length > MAXIMUM_ENVELOPE_LENGTH
                ? null
                : parseCanonicalEnvelope(this.encryptedPrivateKeyInfo);
        this.canonical = parsed != null;
        this.protection = parsed == null ? null : Protection.of(parsed);
    }

    /**
     * @return whether the envelope is short enough to parse
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "encryptedPrivateKeyInfo must not exceed 65536 bytes")
    public boolean isWithinMaximumLength() {
        return encryptedPrivateKeyInfo == null || encryptedPrivateKeyInfo.length <= MAXIMUM_ENVELOPE_LENGTH;
    }

    /**
     * @return whether the bytes are a canonical DER EncryptedPrivateKeyInfo
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = MALFORMED_MESSAGE)
    public boolean isCanonicalEnvelope() {
        return encryptedPrivateKeyInfo == null || !isWithinMaximumLength() || canonical;
    }

    /**
     * Reject unknown key-material properties during deserialization.
     */
    @JsonAnySetter
    @Schema(hidden = true)
    public void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported v2 key-material property: " + property);
    }

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "material must be protected with PBES2 using PBKDF2-HMAC-SHA256 and AES-256-CBC")
    public boolean isPinnedProtectionScheme() {
        if (encryptedPrivateKeyInfo == null || !canonical) {
            return true;
        }
        return protection != null && protection.usesPinnedScheme();
    }

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "material must use a salt of at least 16 bytes, between 100000 and 10000000 iterations, "
            + "a 32-byte derived key when the key length is present, and a 16-byte initialisation vector")
    public boolean isPinnedProtectionParameters() {
        if (protection == null || !protection.usesPinnedScheme()) {
            return true;
        }
        return protection.usesPinnedParameters();
    }

    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "material must carry a non-empty ciphertext of whole 16-byte AES blocks")
    public boolean isWholeCipherBlocks() {
        if (protection == null || !protection.usesPinnedScheme()) {
            return true;
        }
        return protection.hasWholeCipherBlocks();
    }

    /**
     * The parsed envelope, or {@code null} when the bytes are not a canonical DER EncryptedPrivateKeyInfo. Bouncy
     * Castle signals a malformed structure with a range of unchecked exceptions, and each one means the same thing
     * here, so they are caught together rather than enumerated.
     */
    private static EncryptedPrivateKeyInfo parseCanonicalEnvelope(byte[] candidate) {
        try {
            EncryptedPrivateKeyInfo parsed = EncryptedPrivateKeyInfo
                    .getInstance(ASN1Primitive.fromByteArray(candidate));
            return Arrays.equals(candidate, parsed.getEncoded(ASN1Encoding.DER)) ? parsed : null;
        } catch (IOException | RuntimeException e) {
            return null;
        }
    }

    /**
     * Protection facts read from the envelope's algorithm identifier, or absent when it is not PBES2 over PBKDF2 in a
     * shape every field can be read from.
     *
     * <p>
     * The facts are plain values extracted once, so no rule above can call back into the ASN.1 structure and fail with
     * an exception instead of a violation.
     * </p>
     */
    private record Protection(boolean pinnedScheme, int saltLength, BigInteger iterations, BigInteger derivedKeyLength,
            int initialisationVectorLength, int cipherTextLength) {

        /**
         * Reads the facts, treating every way the structure can disagree with the expected shape as absent: a missing
         * parameter block, a missing field, or a field of another syntax.
         */
        private static Protection of(EncryptedPrivateKeyInfo envelope) {
            try {
                AlgorithmIdentifier protection = envelope.getEncryptionAlgorithm();
                if (!PKCSObjectIdentifiers.id_PBES2.equals(protection.getAlgorithm())
                        || protection.getParameters() == null) {
                    return null;
                }

                PBES2Parameters parameters = PBES2Parameters.getInstance(protection.getParameters());
                KeyDerivationFunc keyDerivation = parameters.getKeyDerivationFunc();
                if (!PKCSObjectIdentifiers.id_PBKDF2.equals(keyDerivation.getAlgorithm())
                        || keyDerivation.getParameters() == null) {
                    return null;
                }

                PBKDF2Params derivation = PBKDF2Params.getInstance(keyDerivation.getParameters());
                EncryptionScheme scheme = parameters.getEncryptionScheme();
                AlgorithmIdentifier prf = derivation.getPrf();
                boolean pinnedScheme = PKCSObjectIdentifiers.id_hmacWithSHA256.equals(prf.getAlgorithm())
                        && DERNull.INSTANCE.equals(prf.getParameters())
                        && NISTObjectIdentifiers.id_aes256_CBC.equals(scheme.getAlgorithm());
                return new Protection(pinnedScheme, derivation.getSalt().length, derivation.getIterationCount(),
                        derivation.getKeyLength(), initialisationVectorLength(scheme),
                        envelope.getEncryptedData().length);
            } catch (RuntimeException e) {
                return null;
            }
        }

        private static int initialisationVectorLength(EncryptionScheme scheme) {
            if (scheme.getParameters() == null) {
                return -1;
            }
            try {
                return ASN1OctetString.getInstance(scheme.getParameters()).getOctets().length;
            } catch (RuntimeException e) {
                return -1;
            }
        }

        private boolean usesPinnedScheme() {
            return pinnedScheme;
        }

        private boolean usesPinnedParameters() {
            return saltLength >= MINIMUM_SALT_LENGTH && iterationsWithinBounds() && derivedKeyLengthAccepted()
                    && initialisationVectorLength == INITIALISATION_VECTOR_LENGTH;
        }

        private boolean iterationsWithinBounds() {
            return iterations.compareTo(BigInteger.valueOf(MINIMUM_ITERATIONS)) >= 0
                    && iterations.compareTo(BigInteger.valueOf(MAXIMUM_ITERATIONS)) <= 0;
        }

        private boolean derivedKeyLengthAccepted() {
            return derivedKeyLength == null || derivedKeyLength.equals(BigInteger.valueOf(DERIVED_KEY_LENGTH));
        }

        private boolean hasWholeCipherBlocks() {
            return cipherTextLength > 0 && cipherTextLength % CIPHER_BLOCK_LENGTH == 0;
        }
    }
}
