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
 * Covers the discovery v2 additions to {@link DiscoveryDetailDto}: they round-trip, a v1 run's payload carries the
 * synthesized always-present lists while the optional pair disappears instead of showing up as nulls, and every
 * {@link Resource}-typed or {@link Resource}-keyed value they carry travels as a wire code.
 */
class DiscoveryDetailDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    /** A run against a v1 connector, with resources and effectiveCapabilities exactly as Core synthesizes them. */
    private DiscoveryDetailDto v1Run() {
        DiscoveryDetailDto dto = new DiscoveryDetailDto();
        dto.setUuid("c2f685d4-6a3e-11ec-90d6-0242ac120003");
        dto.setName("nightly-scan");
        dto.setKind("IP-HostName");
        dto.setStatus(DiscoveryStatus.COMPLETED);
        dto.setConnectorStatus(DiscoveryStatus.COMPLETED);
        dto.setConnectorUuid("b9b09548-a97c-4c6a-a06a-e4ee6fc2da98");
        dto.setConnectorName("network-discovery");
        dto.setResources(List.of(Resource.CERTIFICATE));
        dto.setEffectiveCapabilities(List.of());
        return dto;
    }

    @Test
    void roundTripsAllFourV2Fields() throws Exception {
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

        String json = mapper.writeValueAsString(dto);
        DiscoveryDetailDto back = mapper.readValue(json, DiscoveryDetailDto.class);

        // pinned by literal name: a round-trip alone would survive a rename, since it renames both ends at once
        assertTrue(json.contains("\"resources\":"), json);
        assertTrue(json.contains("\"progress\":"), json);
        assertTrue(json.contains("\"runMessages\":"), json);
        assertTrue(json.contains("\"effectiveCapabilities\":"), json);

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
        DiscoveryResourceProgressDto certProgress = new DiscoveryResourceProgressDto();
        certProgress.setProcessed(1L);
        DiscoveryProgressDto progress = new DiscoveryProgressDto();
        progress.setByResource(Map.of(Resource.CERTIFICATE, certProgress));

        DiscoveryDetailDto dto = v1Run();
        dto.setResources(List.of(Resource.CRYPTOGRAPHIC_KEY));
        dto.setProgress(progress);
        dto.setEffectiveCapabilities(List.of(DiscoveryResourceCapability.STOP_RESUME));

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("\"resources\":[\"keys\"]"), json);
        assertTrue(json.contains("\"certificates\""), json);
        assertTrue(json.contains("\"effectiveCapabilities\":[\"stopResume\"]"), json);
        assertFalse(json.contains("CRYPTOGRAPHIC_KEY"), json);
        assertFalse(json.contains("STOP_RESUME"), json);
    }

    @Test
    void v1RunCarriesTheSynthesizedListsAndOmitsTheOptionalPair() throws Exception {
        String json = mapper.writeValueAsString(v1Run());
        DiscoveryDetailDto back = mapper.readValue(json, DiscoveryDetailDto.class);

        // the always-present pair, as Core synthesizes it for a run against a v1 connector
        assertTrue(json.contains("\"resources\":[\"certificates\"]"), json);
        assertTrue(json.contains("\"effectiveCapabilities\":[]"), json);
        assertEquals(List.of(Resource.CERTIFICATE), back.getResources());
        assertEquals(List.of(), back.getEffectiveCapabilities());

        // the genuinely optional pair promises absence, not null
        assertFalse(json.contains("progress"), json);
        assertFalse(json.contains("runMessages"), json);
        assertNull(back.getProgress());
        assertNull(back.getRunMessages());
    }

    @Test
    void keepsEmittingTheExistingNullableV1Fields() throws Exception {
        // @JsonInclude(NON_NULL) must stay field-scoped on the v2 additions: class-scoped it would
        // silently drop these nullable v1 fields from every response.
        String json = mapper.writeValueAsString(v1Run());

        assertTrue(json.contains("\"message\":null"), json);
        assertTrue(json.contains("\"startTime\":null"), json);
        assertTrue(json.contains("\"endTime\":null"), json);
    }
}
