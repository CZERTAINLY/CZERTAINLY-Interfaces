package com.otilm.api.model.connector.v2.cryptography;

import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.common.properties.MetadataAttributeProperties;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;

import java.util.List;

public final class MetadataTestUtils {

    private MetadataTestUtils() {
    }

    public static MetadataAttributeV2 stringMetadata(String name, String value) {
        MetadataAttributeV2 metadata = new MetadataAttributeV2();
        metadata.setName(name);
        metadata.setContentType(AttributeContentType.STRING);
        metadata.setContent(List.of(new StringAttributeContentV2(value)));
        MetadataAttributeProperties properties = new MetadataAttributeProperties();
        properties.setLabel(name);
        metadata.setProperties(properties);
        return metadata;
    }
}
