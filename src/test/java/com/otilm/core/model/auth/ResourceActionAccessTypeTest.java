package com.otilm.core.model.auth;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResourceActionAccessTypeTest {

    private static final Set<ResourceAction> NOT_GRANTABLE = EnumSet.of(
            ResourceAction.NONE,
            ResourceAction.ANY);

    private static final Set<ResourceAction> READ = EnumSet.of(
            ResourceAction.MEMBERS,
            ResourceAction.LIST,
            ResourceAction.DETAIL,
            ResourceAction.EXPORT);

    private static final Set<ResourceAction> SENSITIVE_READ = EnumSet.of(
            ResourceAction.GET_SECRET_CONTENT,
            ResourceAction.GET_PROXY_INSTALLATION);

    @Test
    void everyActionDeclaresAnAccessType() {
        for (ResourceAction action : ResourceAction.values()) {
            assertNotNull(action.getAccessType(), action.name());
        }
    }

    @Test
    void sentinelActionsAreNotGrantable() {
        for (ResourceAction action : NOT_GRANTABLE) {
            assertEquals(ResourceAction.AccessType.NOT_GRANTABLE, action.getAccessType(), action.name());
        }
    }

    @Test
    void readActionsAreClassifiedAsRead() {
        for (ResourceAction action : READ) {
            assertEquals(ResourceAction.AccessType.READ, action.getAccessType(), action.name());
        }
    }

    @Test
    void sensitiveReadActionsAreClassifiedAsSensitiveRead() {
        for (ResourceAction action : SENSITIVE_READ) {
            assertEquals(ResourceAction.AccessType.SENSITIVE_READ, action.getAccessType(), action.name());
        }
    }

    /** Complement of the read and sentinel sets, so a new action grouped with the readable ones fails here. */
    @Test
    void everyRemainingActionIsClassifiedAsWrite() {
        Set<ResourceAction> nonWrite = EnumSet.noneOf(ResourceAction.class);
        nonWrite.addAll(NOT_GRANTABLE);
        nonWrite.addAll(READ);
        nonWrite.addAll(SENSITIVE_READ);

        for (ResourceAction action : ResourceAction.values()) {
            if (nonWrite.contains(action)) {
                continue;
            }
            assertEquals(ResourceAction.AccessType.WRITE, action.getAccessType(), action.name());
        }
    }

    @Test
    void registerIsClassifiedAsWrite() {
        assertEquals(ResourceAction.AccessType.WRITE, ResourceAction.REGISTER.getAccessType());
    }

    @Test
    void onlyPlainReadActionsAreGrantableToAReadOnlyRole() {
        for (ResourceAction action : ResourceAction.values()) {
            assertEquals(READ.contains(action), action.isGrantableToReadOnlyRole(), action.name());
        }
    }

    @Test
    void findByCodeRoundTripsAllValues() {
        for (ResourceAction action : ResourceAction.values()) {
            assertEquals(action, ResourceAction.findByCode(action.getCode()), action.name());
        }
    }

    /** findByCode resolves with findFirst, so a duplicate code would be shadowed rather than rejected. */
    @Test
    void actionCodesAreUnique() {
        long distinctCodes = Arrays.stream(ResourceAction.values())
                .map(ResourceAction::getCode)
                .distinct()
                .count();

        assertEquals(ResourceAction.values().length, distinctCodes);
    }
}
