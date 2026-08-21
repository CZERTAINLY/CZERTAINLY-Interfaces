package com.otilm.api.model.core.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ResourceListViewTest {

    @Test
    void namesTheListViewResourceAfterItsEndpoint() {
        assertEquals("listViews", Resource.LIST_VIEW.getCode());
        assertEquals(Resource.LIST_VIEW, Resource.findByCode("listViews"));
    }

    @Test
    void carriesNoneOfTheObjectLevelTraits() {
        // a view belongs to one user by construction, so it needs no object access, owner, group or custom attributes
        assertFalse(Resource.LIST_VIEW.hasObjectAccess());
        assertFalse(Resource.LIST_VIEW.hasOwner());
        assertFalse(Resource.LIST_VIEW.hasGroups());
        assertFalse(Resource.LIST_VIEW.hasCustomAttributes());
    }
}
