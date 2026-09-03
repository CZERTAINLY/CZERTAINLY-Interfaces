package com.otilm.api.model.connector.cryptography.v2.key;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.cryptography.v2.material.EncryptedKeyMaterialV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body of a successful {@code POST /v2/cryptographyProvider/keys/export}. The platform does not open the envelope, so
 * the descriptor is the connector's account of what it exported rather than proof about the ciphertext.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "ExportKeyResponseV2Dto", description = """
        The exported key, protected, with a description of what it is.

        The key never leaves the connector in the clear: it travels as the same protected envelope the import contract
        accepts. A descriptor accompanies it so the caller can check what it received against its own record before
        assembling anything around material it cannot open.
        """, additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class ExportKeyResponseV2Dto {

    private static final String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    @Valid
    @Schema(description = "Exported key material, protected under the `passphrase` from the request.",
            requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "material is required")
    private EncryptedKeyMaterialV2Dto material;

    @Schema(description = "Durable identity the request supplied, echoed unchanged. Absent when the request carried "
            + "no `keyReference`.", requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            accessMode = Schema.AccessMode.READ_ONLY)
    @Pattern(regexp = UUID_PATTERN, message = "keyReference must be a canonical UUID")
    private String keyReference;

    @Valid
    @Schema(description = """
            Non-sensitive descriptor of the key the connector exported, derived from the material it is returning.

            - A key pair is described by its public key, whose `publicKeySpki` the platform compares with the record it
              already holds for that key.
            - A secret key is described by its algorithm and length; a secret key never carries key material here.
            - A private-key descriptor is not accepted: it would say nothing the platform could check.

            This is the connector's account of what it exported, checked against what the platform expected. It is not
            proof that the envelope holds that key, because the platform does not open the envelope: whoever opens it
            derives the key and can verify it against the certificate.
            """, requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull(message = "keyData is required")
    private KeyDataV2Dto keyData;

    /**
     * A private-key descriptor carries no representation the platform can check, so it cannot stand for an exported key
     * of any type.
     *
     * @return whether the descriptor is one of the two the contract accepts
     */
    @JsonIgnore
    @Schema(hidden = true)
    @AssertTrue(message = "keyData must describe either a public key or a secret key")
    public boolean isCheckableKeyDescriptor() {
        return keyData == null || keyData instanceof PublicKeyDataV2Dto || keyData instanceof SecretKeyDataV2Dto;
    }

    /**
     * Reject unknown export-response properties during deserialization.
     */
    @JsonAnySetter
    @Schema(hidden = true)
    public void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported v2 export response property: " + property);
    }
}
