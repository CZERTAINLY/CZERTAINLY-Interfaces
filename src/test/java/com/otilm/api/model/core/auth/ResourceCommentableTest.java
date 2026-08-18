package com.otilm.api.model.core.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceCommentableTest {

    @Test
    void commentableResourcesContainExactlyTheRoundOneSet() {
        assertEquals(22, Resource.getCommentableResources().size());
        assertTrue(Resource.getCommentableResources().contains(Resource.APPROVAL));
        assertFalse(Resource.getCommentableResources().contains(Resource.CERTIFICATE_REQUEST));
        assertFalse(Resource.getCommentableResources().contains(Resource.COMMENT));
        assertTrue(Resource.CERTIFICATE.commentable());
        assertFalse(Resource.USER.commentable());
    }

    @Test
    void commentableResourcesSetIsImmutable() {
        assertThrows(UnsupportedOperationException.class, () -> Resource.getCommentableResources().add(Resource.USER));
    }
}
