package com.otilm.api.model.connector.v2.cryptography;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.attribute.RequestAttributeV2;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.common.content.data.SecretAttributeContentData;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.common.attribute.v2.content.SecretAttributeContentV2;

import java.util.List;

public final class RedactionTestUtils {

    private RedactionTestUtils() {
    }

    public static RequestAttribute sensitiveAttribute(String marker) {
        var attribute = new RequestAttributeV2();
        attribute.setContentType(AttributeContentType.SECRET);
        attribute.setContent(List.of(new SecretAttributeContentV2(
                "secret-reference",
                new SecretAttributeContentData(marker))));
        return attribute;
    }

    public static MetadataAttributeV2 metadataWithToString(String marker) {
        return new MetadataAttributeV2() {
            @Override
            public String toString() {
                return marker;
            }
        };
    }

    public static Object valueWithToString(String marker) {
        return new Object() {
            @Override
            public String toString() {
                return marker;
            }
        };
    }
}
