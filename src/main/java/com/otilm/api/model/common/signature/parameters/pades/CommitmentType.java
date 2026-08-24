package com.otilm.api.model.common.signature.parameters.pades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.otilm.api.exception.ValidationError;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;

/**
 * The commitment a signer makes, carried as the signed commitment-type attribute of ETSI EN 319 122-1 clause 5.2.3.
 *
 * <p>
 * An enum rather than a free OID string, so a caller cannot inject an arbitrary OID into a signed attribute. The six
 * standard commitments are the whole set; custom commitments would be an additive change.
 * </p>
 */
@Schema(name = "PadesCommitmentType", enumAsRef = true,
        description = "Commitment the signer makes, carried as the signed commitment-type attribute of "
                + "ETSI EN 319 122-1")
public enum CommitmentType implements IPlatformEnum {

    PROOF_OF_ORIGIN(Codes.PROOF_OF_ORIGIN, "Proof of origin",
            "The signer is the originator of the document (OID 1.2.840.113549.1.9.16.6.1)"),
    PROOF_OF_RECEIPT(Codes.PROOF_OF_RECEIPT, "Proof of receipt",
            "The signer received the document (OID 1.2.840.113549.1.9.16.6.2)"),
    PROOF_OF_DELIVERY(Codes.PROOF_OF_DELIVERY, "Proof of delivery",
            "The signer delivered the document (OID 1.2.840.113549.1.9.16.6.3)"),
    PROOF_OF_SENDER(Codes.PROOF_OF_SENDER, "Proof of sender",
            "The signer sent the document (OID 1.2.840.113549.1.9.16.6.4)"),
    PROOF_OF_APPROVAL(Codes.PROOF_OF_APPROVAL, "Proof of approval",
            "The signer approves the content of the document (OID 1.2.840.113549.1.9.16.6.5)"),
    PROOF_OF_CREATION(Codes.PROOF_OF_CREATION, "Proof of creation",
            "The signer created the document (OID 1.2.840.113549.1.9.16.6.6)");

    public static class Codes {

        private Codes() {
        }

        public static final String PROOF_OF_ORIGIN = "proof_of_origin";
        public static final String PROOF_OF_RECEIPT = "proof_of_receipt";
        public static final String PROOF_OF_DELIVERY = "proof_of_delivery";
        public static final String PROOF_OF_SENDER = "proof_of_sender";
        public static final String PROOF_OF_APPROVAL = "proof_of_approval";
        public static final String PROOF_OF_CREATION = "proof_of_creation";
    }

    private static final CommitmentType[] VALUES;

    static {
        VALUES = values();
    }

    private final String code;
    private final String label;
    private final String description;

    CommitmentType(String code, String label, String description) {
        this.code = code;
        this.label = label;
        this.description = description;
    }

    @Override
    @JsonValue
    public String getCode() {
        return this.code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonCreator
    public static CommitmentType findByCode(String code) {
        return Arrays
                .stream(VALUES)
                .filter(k -> k.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ValidationException(ValidationError.create("Unknown commitment type {}", code)));
    }
}
