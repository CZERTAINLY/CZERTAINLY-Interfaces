package com.otilm.api.model.client.discovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.model.connector.discovery.v2.DiscoveryProgressDto;
import com.otilm.api.model.connector.discovery.v2.DiscoveryResourceCapability;
import com.otilm.api.model.connector.discovery.v2.DiscoveryResourceProgressDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.discovery.DiscoveryStatus;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the discovery v2 additions to {@link DiscoveryDetailDto}: they round-trip, they disappear from the payload of
 * a v1 run instead of showing up as nulls, and every {@link Resource}-typed or {@link Resource}-keyed value they carry
 * travels as a wire code.
 */
class DiscoveryDetailDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private DiscoveryDetailDto v1Run() {
        DiscoveryDetailDto dto = new DiscoveryDetailDto();
        dto.setUuid("c2f685d4-6a3e-11ec-90d6-0242ac120003");
        dto.setName("nightly-scan");
        dto.setKind("IP-HostName");
        dto.setStatus(DiscoveryStatus.COMPLETED);
        dto.setConnectorStatus(DiscoveryStatus.COMPLETED);
        dto.setConnectorUuid("b9b09548-a97c-4c6a-a06a-e4ee6fc2da98");
        dto.setConnectorName("network-discovery");
        return dto;
    }

    @Test
    void roundTripsAllFourV2Fields() throws Exception {
        // given
        DiscoveryResourceProgressDto keyProgress = new DiscoveryResourceProgressDto();
        keyProgress.setProcessed(3L);
        DiscoveryProgressDto progress = new DiscoveryProgressDto();
        progress.setProcessed(11L);
        progress.setTotalEstimate(40L);
        progress.setPhase("scanning");
        progress.setByResource(Map.of(Resource.CRYPTOGRAPHIC_KEY, keyProgress));

        DiscoveryDetailDto dto = v1Run();
        dto.setStatus(DiscoveryStatus.STOPPED);
        dto.setResources(List.of(Resource.CERTIFICATE, Resource.CRYPTOGRAPHIC_KEY));
        dto.setProgress(progress);
        dto.setRunMessages(List.of("host 10.0.0.7 refused the connection", "slot 3 unreadable"));
        dto.setEffectiveCapabilities(List.of(DiscoveryResourceCapability.STOP_RESUME));

        // when
        String json = mapper.writeValueAsString(dto);
        DiscoveryDetailDto back = mapper.readValue(json, DiscoveryDetailDto.class);

        // then — the four properties the frontend generates its types from, by name: a round-trip
        // alone would survive a rename, since it renames both ends at once
        assertTrue(json.contains("\"resources\":"), json);
        assertTrue(json.contains("\"progress\":"), json);
        assertTrue(json.contains("\"runMessages\":"), json);
        assertTrue(json.contains("\"effectiveCapabilities\":"), json);

        // and
        assertEquals(List.of(Resource.CERTIFICATE, Resource.CRYPTOGRAPHIC_KEY), back.getResources());
        assertEquals(11L, back.getProgress().getProcessed());
        assertEquals(40L, back.getProgress().getTotalEstimate());
        assertEquals("scanning", back.getProgress().getPhase());
        assertEquals(3L, back.getProgress().getByResource().get(Resource.CRYPTOGRAPHIC_KEY).getProcessed());
        assertEquals(List.of("host 10.0.0.7 refused the connection", "slot 3 unreadable"), back.getRunMessages());
        assertEquals(List.of(DiscoveryResourceCapability.STOP_RESUME), back.getEffectiveCapabilities());
        assertEquals(DiscoveryStatus.STOPPED, back.getStatus());
    }

    @Test
    void resourceAndCapabilityValuesUseWireCodes() throws Exception {
        // given
        DiscoveryResourceProgressDto certProgress = new DiscoveryResourceProgressDto();
        certProgress.setProcessed(1L);
        DiscoveryProgressDto progress = new DiscoveryProgressDto();
        progress.setByResource(Map.of(Resource.CERTIFICATE, certProgress));

        DiscoveryDetailDto dto = v1Run();
        dto.setResources(List.of(Resource.CRYPTOGRAPHIC_KEY));
        dto.setProgress(progress);
        dto.setEffectiveCapabilities(List.of(DiscoveryResourceCapability.STOP_RESUME));

        // when
        String json = mapper.writeValueAsString(dto);

        // then — codes on the resources list, on the byResource map keys, and on the capability
        assertTrue(json.contains("\"resources\":[\"keys\"]"), json);
        assertTrue(json.contains("\"certificates\""), json);
        assertTrue(json.contains("\"effectiveCapabilities\":[\"stopResume\"]"), json);
        assertFalse(json.contains("CRYPTOGRAPHIC_KEY"), json);
        assertFalse(json.contains("STOP_RESUME"), json);
    }

    @Test
    void omitsV2FieldsForAV1Run() throws Exception {
        // given — a run against a v1 connector: none of the four fields apply
        DiscoveryDetailDto dto = v1Run();

        // when
        String json = mapper.writeValueAsString(dto);
        DiscoveryDetailDto back = mapper.readValue(json, DiscoveryDetailDto.class);

        // then — absent from the payload entirely
        assertFalse(json.contains("resources"), json);
        assertFalse(json.contains("progress"), json);
        assertFalse(json.contains("runMessages"), json);
        assertFalse(json.contains("effectiveCapabilities"), json);
        assertNull(back.getResources());
        assertNull(back.getProgress());
        assertNull(back.getRunMessages());
        assertNull(back.getEffectiveCapabilities());
    }

    @Test
    void keepsEmittingTheExistingNullableV1Fields() throws Exception {
        // A class-level @JsonInclude(NON_NULL) would have been the lazy way to omit the new fields,
        // and would have silently dropped these from every v1 response. Pin that it did not happen.
        String json = mapper.writeValueAsString(v1Run());

        assertTrue(json.contains("\"message\":null"), json);
        assertTrue(json.contains("\"startTime\":null"), json);
        assertTrue(json.contains("\"endTime\":null"), json);
    }
}
