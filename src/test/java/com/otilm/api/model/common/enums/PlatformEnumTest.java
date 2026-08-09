package com.otilm.api.model.common.enums;

import com.otilm.api.model.core.notification.NotificationDataCategory;
import com.otilm.api.model.core.protocol.ProtocolChallengeSource;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlatformEnumTest {

    @Test
    void notificationDataCategoryIsRegistered() {
        PlatformEnum entry = PlatformEnum.findByClass(NotificationDataCategory.class);
        assertNotNull(entry);
        assertEquals(NotificationDataCategory.class, entry.getEnumClass());
        assertEquals("NotificationDataCategory", entry.getCode());
    }

    @Test
    void protocolChallengeSourceIsRegistered() {
        PlatformEnum entry = PlatformEnum.findByClass(ProtocolChallengeSource.class);
        assertNotNull(entry);
        assertEquals(PlatformEnum.PROTOCOL_CHALLENGE_SOURCE, entry);
        assertEquals(ProtocolChallengeSource.class, entry.getEnumClass());
        assertEquals("ProtocolChallengeSource", entry.getCode());
    }

    @Test
    void everyEntryHasEnumClassAndLabel() {
        for (PlatformEnum entry : PlatformEnum.values()) {
            assertNotNull(entry.getEnumClass(), "enum class missing for " + entry.name());
            assertTrue(entry.getEnumClass().isEnum(), "not an enum: " + entry.name());
            assertFalse(entry.getLabel().isBlank(), "label missing for " + entry.name());
        }
    }

    @Test
    void registeredEnumClassesAreUnique() {
        Set<Class<?>> seen = new HashSet<>();
        for (PlatformEnum entry : PlatformEnum.values()) {
            assertTrue(seen.add(entry.getEnumClass()),
                    "enum class registered twice: " + entry.getEnumClass().getSimpleName());
        }
    }
}
