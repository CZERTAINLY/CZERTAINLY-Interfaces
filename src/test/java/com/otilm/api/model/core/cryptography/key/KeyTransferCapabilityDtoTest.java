package com.otilm.api.model.core.cryptography.key;

import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * The capability is cached and compared on synchronisation, so two equal reports must compare equal, including the
 * availability flags the capability inherits.
 */
class KeyTransferCapabilityDtoTest {

    @Test
    void equalWhenEveryFieldIsEqual() {
        // given
        KeyTransferCapabilityDto first = capability(true);
        KeyTransferCapabilityDto second = capability(true);

        // when
        // then
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void notEqualWhenAnInheritedFlagDiffers() {
        // given
        KeyTransferCapabilityDto exportable = capability(true);
        KeyTransferCapabilityDto notExportable = capability(false);

        // when
        // then
        assertNotEquals(exportable, notExportable);
    }

    private static KeyTransferCapabilityDto capability(boolean exportAvailable) {
        KeyTransferCapabilityDto capability = new KeyTransferCapabilityDto();
        capability.setImportAvailable(true);
        capability.setExportAvailable(exportAvailable);
        capability.setImportableKeyTypes(Map.of(KeyRequestType.KEY_PAIR, Set.of(KeyAlgorithm.RSA)));
        capability.setExportableKeyTypes(Map.of(KeyRequestType.KEY_PAIR, Set.of(KeyAlgorithm.RSA)));
        return capability;
    }
}
