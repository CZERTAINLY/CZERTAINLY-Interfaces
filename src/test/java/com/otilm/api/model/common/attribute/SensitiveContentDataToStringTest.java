package com.otilm.api.model.common.attribute;

import com.otilm.api.model.common.attribute.common.content.data.CredentialAttributeContentData;
import com.otilm.api.model.common.attribute.common.content.data.SecretAttributeContentData;
import com.otilm.api.model.common.attribute.v2.DataAttributeV2;
import com.otilm.api.model.common.attribute.v3.content.data.ResourceSecretContentData;
import com.otilm.api.model.connector.secrets.content.GenericSecretContent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SensitiveContentDataToStringTest {

    @Test
    void secretContentDataToString_redactsSecret() {
        // given
        var secretMarker = "RECOGNIZABLE-SECRET";
        var content = new SecretAttributeContentData(secretMarker);

        // when
        String representation = content.toString();

        // then
        assertFalse(representation.contains(secretMarker));
        assertTrue(representation.contains("secret=***"));
    }

    @Test
    void credentialContentDataToString_redactsCredentialAttributes() {
        // given
        var credentialMarker = "RECOGNIZABLE-CREDENTIAL";
        var content = new CredentialAttributeContentData();
        content.setKind("Basic");
        content.setAttributes(List.of(sensitiveAttribute(credentialMarker)));

        // when
        String representation = content.toString();

        // then
        assertFalse(representation.contains(credentialMarker));
        assertTrue(representation.contains("kind=Basic"));
        assertTrue(representation.contains("attributes=***"));
    }

    @Test
    void resourceSecretContentDataToString_redactsSecretContent() {
        // given
        var secretMarker = "RECOGNIZABLE-RESOURCE-SECRET";
        var content = new ResourceSecretContentData(
                "secret-uuid",
                "secret-name",
                new GenericSecretContent(secretMarker));

        // when
        String representation = content.toString();

        // then
        assertFalse(representation.contains(secretMarker));
        assertTrue(representation.contains("name=secret-name"));
        assertTrue(representation.contains("content=***"));
    }

    private static DataAttributeV2 sensitiveAttribute(String marker) {
        return new DataAttributeV2() {
            @Override
            public String toString() {
                return marker;
            }
        };
    }
}
