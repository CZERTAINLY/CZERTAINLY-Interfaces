package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import com.otilm.api.model.client.connector.v2.ConnectorInterface;
import com.otilm.api.model.client.connector.v2.FeatureFlag;
import com.otilm.api.model.core.discovery.DiscoveryStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiscoveryV2EnumsTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void runStateSerializesToCode() throws Exception {
        assertEquals("\"running\"", mapper.writeValueAsString(DiscoveryRunState.RUNNING));
        assertEquals(DiscoveryRunState.STOPPED, mapper.readValue("\"stopped\"", DiscoveryRunState.class));
    }

    @Test
    void unknownCodeThrowsValidationException() {
        assertThrows(ValidationException.class, () -> DiscoveryRunState.findByCode("bogus"));
    }

    @Test
    void eventTypeCodes() throws Exception {
        assertEquals("\"resultBatch\"", mapper.writeValueAsString(DiscoveryEventType.RESULT_BATCH));
    }

    @Test
    void resourceCapabilitySerializesToCode() throws Exception {
        assertEquals("\"stopResume\"", mapper.writeValueAsString(DiscoveryResourceCapability.STOP_RESUME));
        assertEquals(DiscoveryResourceCapability.STOP_RESUME, mapper.readValue("\"stopResume\"", DiscoveryResourceCapability.class));
    }

    @Test
    void resourceCapabilityUnknownCodeThrowsValidationException() {
        assertThrows(ValidationException.class, () -> DiscoveryResourceCapability.findByCode("bogus"));
    }

    @Test
    void discoveryStatusGainsStoppedAndCancelled() {
        assertEquals("stopped", DiscoveryStatus.STOPPED.getCode());
        assertEquals("cancelled", DiscoveryStatus.CANCELLED.getCode());
    }

    @Test
    void discoveryFlagsAreEnforcedAndScopedToDiscovery() {
        assertEquals(FeatureFlag.FeatureFlagBehavior.ENFORCED, FeatureFlag.DISCOVERY_STREAMING.getBehavior());
        assertEquals(FeatureFlag.FeatureFlagBehavior.ENFORCED, FeatureFlag.DISCOVERY_STOP_RESUME.getBehavior());
        assertEquals(List.of(ConnectorInterface.DISCOVERY), FeatureFlag.DISCOVERY_STREAMING.getApplicableInterfaces());
        assertEquals(FeatureFlag.DISCOVERY_STOP_RESUME, FeatureFlag.findByCode("discoveryStopResume"));
    }

    @Test
    void stopResumeCapabilityMapsToItsFeatureFlag() {
        // Pinned to the literal enum constant, not reflected back through either accessor.
        assertEquals(FeatureFlag.DISCOVERY_STOP_RESUME, DiscoveryResourceCapability.STOP_RESUME.getFeatureFlag());
    }

    @Test
    void everyResourceCapabilityMapsToAnExistingDiscoveryScopedEnforcedFlag() {
        // Guards against a future capability being added without its feature-flag mapping:
        // a capability whose flag is null, not ENFORCED, or not scoped to the discovery
        // interface fails this test rather than every downstream consumer having to notice.
        for (DiscoveryResourceCapability capability : DiscoveryResourceCapability.values()) {
            FeatureFlag flag = capability.getFeatureFlag();
            assertNotNull(flag, "capability " + capability + " has no mapped FeatureFlag");
            assertEquals(FeatureFlag.FeatureFlagBehavior.ENFORCED, flag.getBehavior(),
                    "capability " + capability + " maps to a non-ENFORCED flag " + flag);
            assertTrue(flag.getApplicableInterfaces() != null && flag.getApplicableInterfaces().contains(ConnectorInterface.DISCOVERY),
                    "capability " + capability + " maps to a flag not scoped to the discovery interface: " + flag);
        }
    }
}
