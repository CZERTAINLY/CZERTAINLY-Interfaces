package com.otilm.api.model.client.connector.v2;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeatureFlagTest {

    @Test
    void certificateRegistrationFlagExists() {
        FeatureFlag flag = FeatureFlag.CERTIFICATE_REGISTRATION;
        assertEquals("certificateRegistration", flag.getCode());
        assertEquals(List.of(ConnectorInterface.AUTHORITY), flag.getApplicableInterfaces());
    }

    @Test
    void certificateStatusPollingFlagExists() {
        FeatureFlag flag = FeatureFlag.CERTIFICATE_STATUS_POLLING;
        assertEquals("certificateStatusPolling", flag.getCode());
        assertEquals(List.of(ConnectorInterface.AUTHORITY), flag.getApplicableInterfaces());
    }

    @Test
    void asynchronousFlag_declaresCryptographyCapability() {
        // given
        var asynchronousCode = "asynchronous";

        // when
        FeatureFlag flag = FeatureFlag.findByCode(asynchronousCode);

        // then
        assertEquals(FeatureFlag.ASYNCHRONOUS, flag);
        assertEquals("Asynchronous", flag.getLabel());
        assertEquals("Supports asynchronous operations", flag.getDescription());
        assertEquals(FeatureFlag.FeatureFlagBehavior.ENFORCED, flag.getBehavior());
        assertEquals(List.of(ConnectorInterface.CRYPTOGRAPHY), flag.getApplicableInterfaces());
    }

    @Test
    void findByCodeRoundTripsAllEntries() {
        for (FeatureFlag f : FeatureFlag.values()) {
            assertEquals(f, FeatureFlag.findByCode(f.getCode()));
        }
    }
}
