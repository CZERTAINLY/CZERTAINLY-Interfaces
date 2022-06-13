package com.czertainly.util;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.common.*;
import com.czertainly.api.model.common.attribute.*;
import com.czertainly.api.model.common.attribute.content.BaseAttributeContent;
import com.czertainly.api.model.core.credential.CredentialDto;
import com.czertainly.core.util.AttributeDefinitionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.czertainly.core.util.AttributeDefinitionUtils.*;

public class AttributeDefinitionUtilsTest {

    @Test
    public void testGetAttribute() {
        String attributeName = "testAttribute";
        List<RequestAttributeDto> attributes = createAttributes(attributeName, 1234);

        RequestAttributeDto attribute = getRequestAttributes(attributeName, attributes);
        Assertions.assertNotNull(attribute);
        Assertions.assertTrue(containsRequestAttributes(attributeName, attributes));
        Assertions.assertEquals(attributes.get(0), attribute);
    }

    @Test
    public void testGetAttributeContent() {
        String attribute1Name = "testAttribute1";
        String attribute2Name = "testAttribute2";

        AttributeDefinition attribute1 = new AttributeDefinition();
        attribute1.setName(attribute1Name);
        attribute1.setContent(new BaseAttributeContent<>(1234));

        AttributeDefinition attribute2 = new AttributeDefinition();
        attribute2.setName(attribute2Name);
        attribute2.setContent(new BaseAttributeContent<>("value"));

        List<AttributeDefinition> attributes = List.of(attribute1, attribute2);

        Object value1 = AttributeDefinitionUtils.getAttributeContent(attribute1Name, attributes);
        Assertions.assertNotNull(value1);
        Assertions.assertTrue(containsAttributeDefinition(attribute1Name, attributes));
        Assertions.assertEquals(attribute1.getContent(), value1);

        Object value2 = AttributeDefinitionUtils.getAttributeContent(attribute2Name, attributes);
        Assertions.assertNotNull(value2);
        Assertions.assertTrue(containsAttributeDefinition(attribute2Name, attributes));
        Assertions.assertEquals(attribute2.getContent(), value2);

        Object value3 = AttributeDefinitionUtils.getAttributeContent("wrongName", attributes);
        Assertions.assertNull(value3);
        Assertions.assertFalse(containsAttributeDefinition("wrongName", attributes));
    }

    @Test
    public void testGetAttributeNameAndUuidValue() {
        String attribute1Name = "testAttribute1";

        HashMap<String, Object> attribute1Value = new HashMap<>();

        HashMap<String, Object> attribute2Value = new HashMap<>();
        attribute2Value.put("uuid", UUID.randomUUID().toString());
        attribute2Value.put("name", "testName");

        attribute1Value.put("data", attribute2Value);
        attribute1Value.put("value", attribute1Name);

        RequestAttributeDto attribute1 = new RequestAttributeDto();
        attribute1.setName(attribute1Name);
        attribute1.setContent(attribute1Value);

        List<RequestAttributeDto> attributes = List.of(attribute1);

        NameAndUuidDto dto = getNameAndUuidData(attribute1Name, attributes);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(attribute2Value.get("uuid"), dto.getUuid());
        Assertions.assertEquals(attribute2Value.get("name"), dto.getName());
    }

    @Test
    public void testGetAttributeCredentialValue() {
        String attribute1Name = "testAttribute1";
        List<RequestAttributeDto> credentialAttributes = createAttributes("credAttr", 987);

        HashMap<String, Object> attribute1Value = new HashMap<>();
        attribute1Value.put("uuid", UUID.randomUUID().toString());
        attribute1Value.put("name", "testName");
        attribute1Value.put("attributes", credentialAttributes);

        RequestAttributeDto attribute1 = new RequestAttributeDto();
        attribute1.setName(attribute1Name);
        attribute1.setContent(attribute1Value);

        List<RequestAttributeDto> attributes = List.of(attribute1);

        CredentialDto dto = getCredentialContent(attribute1Name, attributes);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(attribute1Value.get("uuid"), dto.getUuid());
        Assertions.assertEquals(attribute1Value.get("name"), dto.getName());
        Assertions.assertEquals(credentialAttributes.get(0).getName(), dto.getAttributes().get(0).getName());
    }

    @Test
    public void testAttributeSerialization() {
        String attrData = "[{ \"name\": \"tokenType\", \"content\": { \"value\": \"PEM\" } }," +
                "{ \"name\": \"description\", \"content\": \"DEMO RA Profile\" }," +
                "{ \"name\": \"endEntityProfile\", \"content\": { \"value\": \"DemoTLSServerEndEntityProfile\", \"data\": { \"id\": 0, \"name\": \"DemoTLSServerEndEntityProfile\" } } }," +
                "{ \"name\": \"certificateProfile\", \"content\": \"DemoTLSServerEECertificateProfile\", \"data\": { \"id\": 0, \"name\": \"DemoTLSServerEECertificateProfile\" } }," +
                "{ \"name\": \"certificationAuthority\", \"content\": \"DemoServerSubCA\", \"data\": { \"id\": 0, \"name\": \"DemoServerSubCA\" } }," +
                "{ \"name\": \"sendNotifications\", \"content\": false }," +
                "{ \"name\": \"keyRecoverable\", \"content\": true }]";

        List<AttributeDefinition> attrs = deserialize(attrData);
        Assertions.assertNotNull(attrs);
        Assertions.assertEquals(7, attrs.size());

        NameAndIdDto endEntityProfile = getNameAndIdData("endEntityProfile", AttributeDefinitionUtils.getClientAttributes(attrs));
        Assertions.assertNotNull(endEntityProfile);
        Assertions.assertEquals(0, endEntityProfile.getId());
        Assertions.assertEquals("DemoTLSServerEndEntityProfile", endEntityProfile.getName());

        String serialized = serialize(attrs);
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"tokenType\".*\"content\":.*\"value\":\"PEM\".*$"));
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"keyRecoverable\".*\"content\":true.*$"));
    }

    @Test
    public void testValidateAttributes_success() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("1234");

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_failNoAttribute() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
            // tested method
            validateAttributes(List.of(definition), List.of())
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_failNoValue() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(null); // cause or failure

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_regex() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("1234");
        String validationRegex = "^\\d{4}$";

        Assertions.assertTrue(attributeContent.getValue().matches(validationRegex));

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setValidationRegex(validationRegex);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_regexFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        BaseAttributeContent<String> attributeContent = new BaseAttributeContent<>("12345");
        String validationRegex = "^\\d{4}$";

        Assertions.assertFalse(attributeContent.getValue().matches(validationRegex));

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.STRING);
        definition.setValidationRegex(validationRegex);
        definition.setRequired(true);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(attributeContent);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_credentialMap() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);

        LinkedHashMap<String, Object> credentialData = new LinkedHashMap<>();
        credentialData.put("name", "testName");
        credentialData.put("uuid", "testUuid");
        attribute.setContent(credentialData);

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialDto() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(new CredentialDto());

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialFail() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(123l); // cause or failure

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_unknownAttribute() {
        String attributeName = "testAttribute1";

        AttributeDefinition definition = new AttributeDefinition();
        definition.setName(attributeName);
        definition.setType(AttributeType.STRING);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName("unknown-attribute");  // cause or failure
        attribute.setContent("123");

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributeCallback_success() {
        Set<AttributeCallbackMapping> mappings = new HashSet<>();
        mappings.add(new AttributeCallbackMapping(
                "credentialKind",
                AttributeValueTarget.PATH_VARIABLE,
                "softKeyStore"));

        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext("v1/test");
        callback.setCallbackMethod("GET");
        callback.setMappings(mappings);

        RequestAttributeCallback callbackRequest = new RequestAttributeCallback();
        callbackRequest.setPathVariables(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));

        validateCallback(callback, callbackRequest); // should not throw exception
    }

    @Test
    public void testValidateAttributeCallback_fail() {
        Set<AttributeCallbackMapping> mappings = new HashSet<>();
        mappings.add(new AttributeCallbackMapping(
                "credentialKind",
                AttributeValueTarget.PATH_VARIABLE,
                "softKeyStore"));
        mappings.add(new AttributeCallbackMapping(
                "fromAttribute",
                AttributeType.CREDENTIAL,
                "toQueryParam",
                AttributeValueTarget.REQUEST_PARAMETER));
        mappings.add(new AttributeCallbackMapping(
                "fromAttribute",
                "toBodyKey",
                AttributeValueTarget.BODY));

        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext("core/getCredentials");
        callback.setCallbackMethod("GET");
        callback.setMappings(mappings);

        RequestAttributeCallback callbackRequest = new RequestAttributeCallback();
        callbackRequest.setPathVariables(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));
        callbackRequest.setQueryParameters(Map.ofEntries(Map.entry("toQueryParam", 1234)));


        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validateCallback(callback, callbackRequest));

        Assertions.assertNotNull(exception.getErrors());
        Assertions.assertFalse(exception.getErrors().isEmpty());
        Assertions.assertEquals(2, exception.getErrors().size());
    }
}
