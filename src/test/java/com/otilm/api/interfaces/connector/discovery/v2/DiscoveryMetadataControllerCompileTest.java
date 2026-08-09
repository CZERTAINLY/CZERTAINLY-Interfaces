package com.otilm.api.interfaces.connector.discovery.v2;

import com.otilm.api.model.common.attribute.common.BaseAttribute;
import com.otilm.api.model.connector.discovery.v2.DiscoverySupportedResourceDto;
import com.otilm.api.model.core.auth.Resource;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DiscoveryMetadataControllerCompileTest {

    /** Minimal mock impl — compilation alone proves the interface signatures are coherent. */
    static class Mock implements DiscoveryMetadataController {
        @Override
        public List<DiscoverySupportedResourceDto> listSupportedResources() {
            return List.of();
        }

        @Override
        public List<BaseAttribute> listRunAttributes() {
            return List.of();
        }

        @Override
        public List<BaseAttribute> listResourceAttributes(Resource resource) {
            return List.of();
        }
    }

    @Test
    void mockImplementsInterface() {
        assertNotNull(new Mock());
    }
}
