package com.otilm.api.model.connector.v2.cryptography;

import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.OperationResponseValidator;
import com.otilm.api.model.connector.cryptography.v2.key.KeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureResponseDataV2Dto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationResponseValidatorTest {

    @Test
    void validateCreate_rejectsMissingModeResponseAndWrongStatus() {
        // given
        var synchronousResponse = ResponseEntity.ok(new SecretKeyDataResponseV2Dto());
        var acceptedResponse = ResponseEntity.accepted().body(new SecretKeyDataResponseV2Dto());

        // when
        Executable missingMode = () -> OperationResponseValidator.validateCreate(null, synchronousResponse);
        Executable missingResponse = () ->
                OperationResponseValidator.validateCreate(OperationExecutionMode.SYNCHRONOUS, null);
        Executable wrongStatus = () ->
                OperationResponseValidator.validateCreate(OperationExecutionMode.SYNCHRONOUS, acceptedResponse);

        // then
        assertThrows(IllegalArgumentException.class, missingMode);
        assertThrows(IllegalArgumentException.class, missingResponse);
        assertThrows(IllegalArgumentException.class, wrongStatus);
    }

    @Test
    void validateCreate_requiresTrackingMetadataForAsynchronousResponse() {
        // given
        var responseWithoutMetadata = ResponseEntity.accepted().body(new SecretKeyDataResponseV2Dto());
        var completeBody = new SecretKeyDataResponseV2Dto();
        completeBody.setOperationMeta(List.of(stringMetadata("operationId", "operation-1")));
        var completeResponse = ResponseEntity.accepted().body(completeBody);

        // when
        Executable missingMetadata = () ->
                OperationResponseValidator.validateCreate(OperationExecutionMode.ASYNCHRONOUS, responseWithoutMetadata);
        Executable validateCompleteResponse = () ->
                OperationResponseValidator.validateCreate(OperationExecutionMode.ASYNCHRONOUS, completeResponse);

        // then
        assertThrows(IllegalArgumentException.class, missingMetadata);
        assertDoesNotThrow(validateCompleteResponse);
    }

    @Test
    void validateSign_requiresBodyAndCompletedSignaturesForSynchronousResponse() {
        // given
        ResponseEntity<SignDataResponseV2Dto> missingBody = ResponseEntity.ok().build();
        var missingSignatures = ResponseEntity.ok(new SignDataResponseV2Dto());
        var completeBody = new SignDataResponseV2Dto();
        completeBody.setSignatures(List.of(new SignatureResponseDataV2Dto(new byte[]{1}, "item-1", null)));
        var completeResponse = ResponseEntity.ok(completeBody);

        // when
        Executable validateMissingBody = () ->
                OperationResponseValidator.validateSign(OperationExecutionMode.SYNCHRONOUS, missingBody);
        Executable validateMissingSignatures = () ->
                OperationResponseValidator.validateSign(OperationExecutionMode.SYNCHRONOUS, missingSignatures);
        Executable validateComplete = () ->
                OperationResponseValidator.validateSign(OperationExecutionMode.SYNCHRONOUS, completeResponse);

        // then
        assertThrows(IllegalArgumentException.class, validateMissingBody);
        assertThrows(IllegalArgumentException.class, validateMissingSignatures);
        assertDoesNotThrow(validateComplete);
    }

    @Test
    void validateSign_requiresTrackingMetadataForAsynchronousResponse() {
        // given
        var responseWithoutMetadata = ResponseEntity.accepted().body(new SignDataResponseV2Dto());
        var completeBody = new SignDataResponseV2Dto();
        completeBody.setSignOperationMeta(List.of(stringMetadata("operationId", "operation-1")));
        var completeResponse = ResponseEntity.accepted().body(completeBody);

        // when
        Executable missingMetadata = () ->
                OperationResponseValidator.validateSign(OperationExecutionMode.ASYNCHRONOUS, responseWithoutMetadata);
        Executable validateCompleteResponse = () ->
                OperationResponseValidator.validateSign(OperationExecutionMode.ASYNCHRONOUS, completeResponse);

        // then
        assertThrows(IllegalArgumentException.class, missingMetadata);
        assertDoesNotThrow(validateCompleteResponse);
    }

    @Test
    void validateDestroy_enforcesModeSpecificStatusAndAsyncMetadata() {
        // given
        ResponseEntity<KeyDataResponseV2Dto> synchronous = ResponseEntity.noContent().build();
        var asynchronousWithoutMetadata = ResponseEntity.accepted().body(new KeyDataResponseV2Dto());
        var completeAsyncBody = new KeyDataResponseV2Dto();
        completeAsyncBody.setOperationMeta(List.of(stringMetadata("operationId", "operation-1")));
        var completeAsynchronous = ResponseEntity.accepted().body(completeAsyncBody);

        // when
        Executable validSynchronous = () ->
                OperationResponseValidator.validateDestroy(OperationExecutionMode.SYNCHRONOUS, synchronous);
        Executable missingAsyncMetadata = () ->
                OperationResponseValidator.validateDestroy(
                        OperationExecutionMode.ASYNCHRONOUS, asynchronousWithoutMetadata);
        Executable validAsynchronous = () ->
                OperationResponseValidator.validateDestroy(OperationExecutionMode.ASYNCHRONOUS, completeAsynchronous);

        // then
        assertDoesNotThrow(validSynchronous);
        assertThrows(IllegalArgumentException.class, missingAsyncMetadata);
        assertDoesNotThrow(validAsynchronous);
    }
}
