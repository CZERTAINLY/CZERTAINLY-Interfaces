package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyCreationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDestructionStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.CipherDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.DecryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.EncryptDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignOperationStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.VerifyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.IdentifiedDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenStatusResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.validation.AsynchronousResponse;
import com.otilm.api.model.connector.cryptography.v2.validation.SynchronousResponse;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Validates that an async-capable connector response honors the caller-selected execution mode.
 */
public final class OperationResponseValidator {

    private final Validator validator;

    public OperationResponseValidator(Validator validator) {
        this.validator = Objects.requireNonNull(validator, "validator is required");
    }

    private static OperationValidationResult validate(Runnable validation) {
        try {
            validation.run();
            return OperationValidationResult.valid();
        } catch (IllegalArgumentException e) {
            return OperationValidationResult.invalid(e);
        }
    }

    private static void requireExecutionMode(OperationExecutionMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Execution mode is required");
        }
    }

    private static void requireResponseStatus(ResponseEntity<?> response, HttpStatus expectedStatus) {
        if (response == null) {
            throw new IllegalArgumentException("Connector returned no response");
        }
        if (response.getStatusCode().value() != expectedStatus.value()) {
            throw new IllegalArgumentException("Connector returned HTTP " + response.getStatusCode().value()
                    + "; expected HTTP " + expectedStatus.value());
        }
    }

    private static <T> T requireBody(ResponseEntity<T> response) {
        if (response.getBody() == null) {
            throw new IllegalArgumentException("Connector response body is required");
        }
        return response.getBody();
    }

    private static void requireEmptyBody(ResponseEntity<?> response) {
        if (response.getBody() != null) {
            throw new IllegalArgumentException("Connector response body must be empty");
        }
    }

    private static void requireMatchingIdentifiers(List<? extends IdentifiedDataV2Dto> requestItems,
            List<? extends IdentifiedDataV2Dto> responseItems, String errorMessage) {
        if (requestItems == null) {
            throw new IllegalArgumentException("Request data is required");
        }
        Set<String> requestIdentifiers = requestItems
                .stream()
                .map(IdentifiedDataV2Dto::getIdentifier)
                .collect(Collectors.toSet());
        List<String> responseIdentifiers = responseItems.stream().map(IdentifiedDataV2Dto::getIdentifier).toList();
        Set<String> uniqueResponseIdentifiers = new HashSet<>(responseIdentifiers);
        if (uniqueResponseIdentifiers.size() != responseIdentifiers.size()
                || !uniqueResponseIdentifiers.equals(requestIdentifiers)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public OperationValidationResult validateTokenStatus(TokenStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateAttributeList(List<BaseAttribute> response) {
        return validateResponseElements(response, "attribute");
    }

    public OperationValidationResult validateKeyUsageList(List<KeyUsage> response) {
        return validateResponseElements(response, "key usage");
    }

    public OperationValidationResult validateSupportedKeyRequestTypes(List<KeyRequestType> response) {
        return validateResponseElements(response, "key request type");
    }

    public OperationValidationResult validateCreateKey(CreateKeyRequestV2Dto request,
            ResponseEntity<? extends KeyCreationResponseV2Dto> response) {
        return validate(() -> {
            if (request == null) {
                throw new IllegalArgumentException("Key creation request is required");
            }
            if (request.getKeyRequestType() == null) {
                throw new IllegalArgumentException("Key request type is required");
            }
            validateOperationResponseConstraints(request.getExecutionMode(), response, SynchronousBody.REQUIRED);
            KeyRequestType responseType = requireBody(response).getKeyRequestType();
            if (request.getKeyRequestType() != responseType) {
                throw new IllegalArgumentException("Connector returned key request type " + responseType + "; expected "
                        + request.getKeyRequestType());
            }
        });
    }

    public OperationValidationResult validateCreateKeyStatus(KeyCreationStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateDestroy(OperationExecutionMode mode,
            ResponseEntity<KeyOperationResponseV2Dto> response) {
        return validateOperationResponse(mode, response, SynchronousBody.EMPTY);
    }

    private OperationValidationResult validateOperationResponse(OperationExecutionMode mode, ResponseEntity<?> response,
            SynchronousBody synchronousBody) {
        return validate(() -> validateOperationResponseConstraints(mode, response, synchronousBody));
    }

    public OperationValidationResult validateDestroyKeyStatus(KeyDestructionStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateEncrypt(CipherDataRequestV2Dto request,
            EncryptDataResponseV2Dto response) {
        return validate(() -> {
            requireRequest(request, "Encryption");
            validateRequiredBean(response);
            requireMatchingIdentifiers(request.getCipherData(), response.getEncryptedData(),
                    "Encryption response identifiers must match request identifiers");
        });
    }

    public OperationValidationResult validateDecrypt(CipherDataRequestV2Dto request,
            DecryptDataResponseV2Dto response) {
        return validate(() -> {
            requireRequest(request, "Decryption");
            validateRequiredBean(response);
            requireMatchingIdentifiers(request.getCipherData(), response.getDecryptedData(),
                    "Decryption response identifiers must match request identifiers");
        });
    }

    public OperationValidationResult validateVerify(VerifyDataRequestV2Dto request, VerifyDataResponseV2Dto response) {
        return validate(() -> {
            requireRequest(request, "Verification");
            validateRequiredBean(response);
            requireMatchingIdentifiers(request.getData(), response.getVerifications(),
                    "Verification response identifiers must match request identifiers");
        });
    }

    public OperationValidationResult validateRandom(RandomDataRequestV2Dto request, RandomDataResponseV2Dto response) {
        return validate(() -> {
            requireRequest(request, "Random-data");
            validateRequiredBean(response);
            if (response.getData().length != request.getLength()) {
                throw new IllegalArgumentException("Connector returned " + response.getData().length
                        + " random bytes; expected " + request.getLength());
            }
        });
    }

    public OperationValidationResult validateSignStatus(SignOperationStatusResponseV2Dto response) {
        return validateBeanConstraints(response);
    }

    public OperationValidationResult validateSign(SignDataRequestV2Dto request,
            ResponseEntity<SignDataResponseV2Dto> response) {
        return validate(() -> {
            if (request == null) {
                throw new IllegalArgumentException("Signing request is required");
            }
            validateOperationResponseConstraints(request.getExecutionMode(), response, SynchronousBody.REQUIRED);
            if (request.getExecutionMode() == OperationExecutionMode.SYNCHRONOUS) {
                requireMatchingIdentifiers(request.getData(), requireBody(response).getSignatures(),
                        "Synchronous signing response identifiers must match request identifiers");
            }
        });
    }

    private static void requireRequest(Object request, String operation) {
        if (request == null) {
            throw new IllegalArgumentException(operation + " request is required");
        }
    }

    private void validateOperationResponseConstraints(OperationExecutionMode mode, ResponseEntity<?> response,
            SynchronousBody synchronousBody) {
        requireExecutionMode(mode);
        if (mode == OperationExecutionMode.SYNCHRONOUS) {
            requireResponseStatus(response, HttpStatus.OK);
            if (synchronousBody == SynchronousBody.REQUIRED) {
                validateBean(requireBody(response), SynchronousResponse.class);
            } else {
                requireEmptyBody(response);
            }
        } else {
            requireResponseStatus(response, HttpStatus.ACCEPTED);
            validateBean(requireBody(response), AsynchronousResponse.class);
        }
    }

    private OperationValidationResult validateBeanConstraints(Object response) {
        return validate(() -> validateRequiredBean(response));
    }

    private void validateRequiredBean(Object response) {
        if (response == null) {
            throw new IllegalArgumentException("Connector response body is required");
        }
        validateBean(response, Default.class);
    }

    private OperationValidationResult validateResponseElements(List<?> response, String itemName) {
        return validate(() -> {
            if (response == null) {
                throw new IllegalArgumentException("Connector response body is required");
            }
            for (Object item : response) {
                if (item == null) {
                    throw new IllegalArgumentException("Connector response must not contain a null " + itemName);
                }
                validateBean(item, Default.class);
            }
        });
    }

    private void validateBean(Object body, Class<?> group) {
        Set<ConstraintViolation<Object>> violations = validator.validate(body, group);
        if (!violations.isEmpty()) {
            String details = violations
                    .stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Connector response validation failed: " + details);
        }
    }

    private enum SynchronousBody {
        REQUIRED,
        EMPTY
    }
}
