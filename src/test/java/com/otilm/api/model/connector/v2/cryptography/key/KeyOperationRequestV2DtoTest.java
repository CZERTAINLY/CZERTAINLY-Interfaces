package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationCancelRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationStatusRequestV2Dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.otilm.api.model.connector.v2.cryptography.RedactionTestUtils.metadataWithToString;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.validate;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

class KeyOperationRequestV2DtoTest {

    @Test
    void cancelRequestToString_includesOperationMetadata() {
        // given
        var metadataMarker = "OPAQUE-OPERATION-HANDLE";
        var request = new KeyOperationCancelRequestV2Dto();
        request.setOperationMeta(List.of(metadataWithToString(metadataMarker)));

        // when
        var representation = request.toString();

        // then
        assertTrue(representation.contains(metadataMarker));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("requests")
    void validation_rejectsMissingEmptyAndNullTrackingMetadata(Object request) {
        // given
        var operationMeta = request instanceof KeyOperationStatusRequestV2Dto status
                ? new MetadataAccess(status) : new MetadataAccess((KeyOperationCancelRequestV2Dto) request);

        // when
        var missingMetadata = validate(request);
        operationMeta.set(List.of());
        var emptyMetadata = validate(request);
        operationMeta.set(Collections.singletonList(null));
        var nullMetadata = validate(request);

        // then
        assertViolation(missingMetadata, "operationMeta", NotEmpty.class);
        assertViolation(emptyMetadata, "operationMeta", NotEmpty.class);
        assertViolation(nullMetadata, "operationMeta[0].<list element>", NotNull.class);
    }

    private static Stream<Named<Object>> requests() {
        return Stream.of(
                named("status request", new KeyOperationStatusRequestV2Dto()),
                named("cancel request", new KeyOperationCancelRequestV2Dto()));
    }

    private record MetadataAccess(
            java.util.function.Consumer<List<com.otilm.api.model.common.attribute.v2.MetadataAttributeV2>>
            setter) {
        MetadataAccess(KeyOperationStatusRequestV2Dto request) {
            this(request::setOperationMeta);
        }

        MetadataAccess(KeyOperationCancelRequestV2Dto request) {
            this(request::setOperationMeta);
        }

        void set(List<com.otilm.api.model.common.attribute.v2.MetadataAttributeV2> metadata) {
            setter.accept(metadata);
        }
    }
}
