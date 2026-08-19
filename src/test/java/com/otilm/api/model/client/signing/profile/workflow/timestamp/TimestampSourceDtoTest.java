package com.otilm.api.model.client.signing.profile.workflow.timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.Validator;
import java.util.UUID;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimestampSourceDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void eachArmCarriesItsOwnType() {
        assertEquals(TimestampSourceType.INTERNAL, new InternalTimestampSourceRequestDto(UUID.randomUUID()).getType());
        assertEquals(TimestampSourceType.INTERNAL,
                new InternalTimestampSourceDto(new NameAndUuidDto(UUID.randomUUID(), "tsa-profile")).getType());
    }

    @Test
    void theRequestArmRoundTripsThroughTheUnion() throws Exception {
        UUID profileUuid = UUID.randomUUID();
        String json = mapper.writeValueAsString(new InternalTimestampSourceRequestDto(profileUuid));

        assertTrue(json.contains("\"type\":\"internal\""), json);

        TimestampSourceRequestDto decoded = mapper.readValue(json, TimestampSourceRequestDto.class);
        assertEquals(profileUuid,
                assertInstanceOf(InternalTimestampSourceRequestDto.class, decoded).signingProfileUuid());
    }

    @Test
    void theResponseArmRoundTripsThroughTheUnion() throws Exception {
        NameAndUuidDto profile = new NameAndUuidDto(UUID.randomUUID(), "tsa-profile");
        String json = mapper.writeValueAsString(new InternalTimestampSourceDto(profile));

        assertTrue(json.contains("\"type\":\"internal\""), json);

        TimestampSourceDto decoded = mapper.readValue(json, TimestampSourceDto.class);
        assertEquals(profile, assertInstanceOf(InternalTimestampSourceDto.class, decoded).signingProfile());
    }

    /** An unknown discriminator must fail loudly rather than bind to the only arm that happens to exist. */
    @Test
    void anUnknownTypeIsRejected() {
        String json = "{\"type\":\"external_rfc3161\",\"url\":\"https://tsa.example\"}";
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, TimestampSourceRequestDto.class));
    }

    @Test
    void aMissingTypeIsRejected() {
        String json = "{\"signingProfileUuid\":\"" + UUID.randomUUID() + "\"}";
        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, TimestampSourceRequestDto.class));
    }

    @Test
    void theReferencedProfileIsRequired() {
        assertFalse(VALIDATOR.validate(new InternalTimestampSourceRequestDto(null)).isEmpty());
        assertTrue(VALIDATOR.validate(new InternalTimestampSourceRequestDto(UUID.randomUUID())).isEmpty());
    }

    @Test
    void theReferencedProfileIsRequiredOnTheResponseArm() {
        assertFalse(VALIDATOR.validate(new InternalTimestampSourceDto(null)).isEmpty());
        assertTrue(VALIDATOR
                .validate(new InternalTimestampSourceDto(new NameAndUuidDto(UUID.randomUUID(), "tsa-profile")))
                .isEmpty());
    }

    @Test
    void anUnknownWireCodeIsRejected() {
        assertThrows(ValidationException.class, () -> TimestampSourceType.findByCode("external_rfc3161"));
    }

    @Test
    void wireCodeIsPinned() {
        assertEquals("internal", TimestampSourceType.INTERNAL.getCode());
        assertEquals(TimestampSourceType.INTERNAL.getCode(), TimestampSourceType.Codes.INTERNAL);
        assertEquals(TimestampSourceType.INTERNAL, TimestampSourceType.findByCode("internal"));
    }
}
