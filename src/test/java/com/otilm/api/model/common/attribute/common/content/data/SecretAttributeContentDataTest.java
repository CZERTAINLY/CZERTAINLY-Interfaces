package com.otilm.api.model.common.attribute.common.content.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecretAttributeContentDataTest {

    private static final String SECRET = "s3cr3t-value";

    @Test
    void toStringRedactsTheSecret() {
        // given
        SecretAttributeContentData data = new SecretAttributeContentData(SECRET, ProtectionLevel.ENCRYPTED);

        // when
        String rendered = data.toString();

        // then
        assertFalse(rendered.contains(SECRET), () -> "secret leaked into " + rendered);
        assertTrue(rendered.contains("protectionLevel"));
    }

    @Test
    void toStringStillSaysWhetherASecretIsPresent() {
        // given
        SecretAttributeContentData withSecret = new SecretAttributeContentData(SECRET);
        SecretAttributeContentData withoutSecret = new SecretAttributeContentData();

        // when
        // then
        assertNotEquals(withoutSecret.toString(), withSecret.toString(),
                "a present secret must be distinguishable from an absent one without printing it");
    }
}
