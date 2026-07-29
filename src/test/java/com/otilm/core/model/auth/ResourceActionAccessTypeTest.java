package com.otilm.core.model.auth;

import org.junit.jupiter.api.Test;

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
            ResourceAction.EXPORT,
            ResourceAction.GET_PROXY_INSTALLATION);

    private static final Set<ResourceAction> SENSITIVE_READ = EnumSet.of(
            ResourceAction.GET_SECRET_CONTENT);

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
    void secretContentIsClassifiedAsSensitiveRead() {
        assertEquals(ResourceAction.AccessType.SENSITIVE_READ, ResourceAction.GET_SECRET_CONTENT.getAccessType());
    }

    /**
     * Complement of the read and sentinel sets. A newly added action that is mistakenly grouped with the
     * readable actions fails here rather than silently widening what a read-only role is granted.
     */
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
    void registerResolvesFromItsCode() {
        assertEquals(ResourceAction.REGISTER, ResourceAction.findByCode("register"));
    }
}
