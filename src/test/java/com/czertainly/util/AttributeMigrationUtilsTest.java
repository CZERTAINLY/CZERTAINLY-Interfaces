package com.czertainly.util;

import com.czertainly.api.model.common.attribute.AttributeDefinition;
import com.czertainly.core.util.AttributeDefinitionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czertainly.core.util.AttributeMigrationUtils.getNewAttributes;

public class AttributeMigrationUtilsTest {

    @Test
    public void testMultiSelectAttribute() throws JsonProcessingException {
        String attribute = "[{\"uuid\":\"87a94421-c5d8-4a23-bb2c-bbee76cb4eaa\",\"name\":\"caInstance\",\"label\":\"CA Instance\",\"type\":\"LIST\",\"required\":true,\"readOnly\":false,\"editable\":true,\"visible\":true,\"multiValue\":false,\"description\":null,\"validationRegex\":null,\"dependsOn\":null,\"attributeCallback\":null,\"value\":\"lab02-ADCS\"},{\"uuid\":\"25b35cf2-45cb-4e4c-b5b3-a99ecb8aa8e6\",\"name\":\"caAdcs\",\"label\":\"Certificate Authority\",\"type\":\"LIST\",\"required\":false,\"readOnly\":false,\"editable\":true,\"visible\":true,\"multiValue\":true,\"description\":null,\"validationRegex\":null,\"dependsOn\":null,\"attributeCallback\":{\"callbackContext\":\"/v1/discoveryProvider/listCertificateAuthority/{caInstance}\",\"callbackMethod\":\"GET\",\"mappings\":[{\"from\":\"caInstance\",\"attributeType\":null,\"to\":\"caInstance\",\"targets\":[\"pathVariable\"],\"value\":null}]},\"value\":[\"vmi307469.3key.local\\\\Demo MS Sub CA\"]},{\"uuid\":\"83c0f20b-4789-44f2-abd2-a84c131d5e97\",\"name\":\"template\",\"label\":\"Template\",\"type\":\"LIST\",\"required\":false,\"readOnly\":false,\"editable\":true,\"visible\":true,\"multiValue\":true,\"description\":null,\"validationRegex\":null,\"dependsOn\":null,\"attributeCallback\":{\"callbackContext\":\"/v1/discoveryProvider/listTemplate/{caInstance}\",\"callbackMethod\":\"GET\",\"mappings\":[{\"from\":\"caInstance\",\"attributeType\":null,\"to\":\"caInstance\",\"targets\":[\"pathVariable\"],\"value\":null}]},\"value\":[\"Administrator\",\"WebServer\",\"CodeSigning\"]}]";

        ObjectMapper mapper = new ObjectMapper();

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();

        List<Map<String, Object>> oldAttributeValue = mapper.readValue(attribute, new TypeReference<>() {
        });
        for (Map<String, Object> item : oldAttributeValue) {
            attributeDefinitions.add(getNewAttributes(item));
        }
        String serializedAttributes = AttributeDefinitionUtils.serialize(attributeDefinitions);

        Assertions.assertNotNull(serializedAttributes);
        Assertions.assertTrue(attributeDefinitions.get(1).isMultiSelect());
    }

}
