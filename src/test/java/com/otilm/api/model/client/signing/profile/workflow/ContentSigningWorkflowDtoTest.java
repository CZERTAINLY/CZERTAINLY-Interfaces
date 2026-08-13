package com.otilm.api.model.client.signing.profile.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.InternalTimestampSourceDto;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.InternalTimestampSourceRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.timestamp.TimestampSourceType;
import com.otilm.api.model.common.NameAndUuidDto;
import com.otilm.api.model.common.signature.SignatureFamily;
import com.otilm.api.model.common.signature.SignatureLevel;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentSigningWorkflowDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private ContentSigningWorkflowRequestDto fullyConfiguredRequest() {
        ContentSigningWorkflowRequestDto dto = new ContentSigningWorkflowRequestDto();
        dto.setSignatureFormattingConnectorUuid(UUID.randomUUID());
        dto.setFamily(SignatureFamily.PADES);
        dto.setMaxLevel(SignatureLevel.ARCHIVAL);
        dto.setTimestampSource(new InternalTimestampSourceRequestDto(UUID.randomUUID()));
        dto.setDocumentSizeCap(5_242_880L);
        return dto;
    }

    /** The request travels inside a Signing Profile, so it has to survive the polymorphic base, not just itself. */
    @Test
    void theRequestRoundTripsThroughTheWorkflowUnion() throws Exception {
        ContentSigningWorkflowRequestDto original = fullyConfiguredRequest();

        String json = mapper.writeValueAsString(original);
        WorkflowRequestDto decoded = mapper.readValue(json, WorkflowRequestDto.class);

        ContentSigningWorkflowRequestDto workflow = assertInstanceOf(ContentSigningWorkflowRequestDto.class, decoded);
        assertEquals(SigningWorkflowType.CONTENT_SIGNING, workflow.getType());
        assertEquals(SignatureFamily.PADES, workflow.getFamily());
        assertEquals(SignatureLevel.ARCHIVAL, workflow.getMaxLevel());
        assertEquals(5_242_880L, workflow.getDocumentSizeCap());
        assertEquals(TimestampSourceType.INTERNAL, workflow.getTimestampSource().getType());
        assertEquals(original.getTimestampSource(), workflow.getTimestampSource());
    }

    @Test
    void theResponseRoundTripsThroughTheWorkflowUnion() throws Exception {
        ContentSigningWorkflowDto original = new ContentSigningWorkflowDto();
        original.setFamily(SignatureFamily.XADES);
        original.setMaxLevel(SignatureLevel.TIMESTAMPED);
        original.setDocumentSizeCap(1024L);
        original.setTimestampSource(new InternalTimestampSourceDto(new NameAndUuidDto(UUID.randomUUID(), "tsa")));

        WorkflowDto decoded = mapper.readValue(mapper.writeValueAsString(original), WorkflowDto.class);

        ContentSigningWorkflowDto workflow = assertInstanceOf(ContentSigningWorkflowDto.class, decoded);
        assertEquals(SignatureFamily.XADES, workflow.getFamily());
        assertEquals(SignatureLevel.TIMESTAMPED, workflow.getMaxLevel());
        assertEquals(1024L, workflow.getDocumentSizeCap());
        assertEquals(TimestampSourceType.INTERNAL, workflow.getTimestampSource().getType());
        assertEquals(original.getTimestampSource(), workflow.getTimestampSource());
    }

    /** A delegated-signing profile carries none of these fields. */
    @Test
    void allFourFieldsAreOptionalOnTheWire() throws Exception {
        WorkflowRequestDto decoded = mapper.readValue("{\"type\":\"content_signing\"}", WorkflowRequestDto.class);

        ContentSigningWorkflowRequestDto workflow = assertInstanceOf(ContentSigningWorkflowRequestDto.class, decoded);
        assertNull(workflow.getFamily());
        assertNull(workflow.getMaxLevel());
        assertNull(workflow.getTimestampSource());
        assertNull(workflow.getDocumentSizeCap());
    }

    @Test
    void theFamilyAndLevelTravelAsTheirWireCodes() throws Exception {
        String json = mapper.writeValueAsString(fullyConfiguredRequest());

        assertTrue(json.contains("\"family\":\"pades\""), json);
        assertTrue(json.contains("\"maxLevel\":\"archival\""), json);
    }
}
