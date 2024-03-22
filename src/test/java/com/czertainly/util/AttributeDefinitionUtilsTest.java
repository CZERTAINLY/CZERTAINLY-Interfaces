package com.czertainly.util;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.NameAndIdDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.AttributeType;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallback;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeCallbackMapping;
import com.czertainly.api.model.common.attribute.v2.callback.AttributeValueTarget;
import com.czertainly.api.model.common.attribute.v2.callback.RequestAttributeCallback;
import com.czertainly.api.model.common.attribute.v2.constraint.AttributeConstraintType;
import com.czertainly.api.model.common.attribute.v2.constraint.DateTimeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RangeAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.RegexpAttributeConstraint;
import com.czertainly.api.model.common.attribute.v2.constraint.data.DateTimeAttributeConstraintData;
import com.czertainly.api.model.common.attribute.v2.constraint.data.RangeAttributeConstraintData;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.czertainly.api.model.common.attribute.v2.properties.DataAttributeProperties;
import com.czertainly.core.util.AttributeDefinitionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.czertainly.core.util.AttributeDefinitionUtils.*;

public class AttributeDefinitionUtilsTest {

    @Test
    public void testGetAttribute() {
        String attributeName = "testAttribute";
        List<RequestAttributeDto> attributes = createAttributes(attributeName, List.of(new IntegerAttributeContent(1234)));

        RequestAttributeDto attribute = getRequestAttributes(attributeName, attributes);
        Assertions.assertNotNull(attribute);
        Assertions.assertTrue(containsRequestAttributes(attributeName, attributes));
        Assertions.assertEquals(attributes.get(0), attribute);
    }

    @Test
    public void testGetAttributeContent() {
        String attribute1Name = "testAttribute1";
        String attribute2Name = "testAttribute2";

        DataAttribute attribute1 = new DataAttribute();
        attribute1.setName(attribute1Name);
        attribute1.setContent(List.of(new IntegerAttributeContent(1234)));

        DataAttribute attribute2 = new DataAttribute();
        attribute2.setName(attribute2Name);
        attribute2.setContent(List.of(new StringAttributeContent("value")));

        List<BaseAttribute> attributes = List.of(attribute1, attribute2);

        Object value1 = AttributeDefinitionUtils.getAttributeContent(attribute1Name, attributes, false);
        Assertions.assertNotNull(value1);
        Assertions.assertTrue(containsAttributeDefinition(attribute1Name, attributes));
        Assertions.assertEquals(attribute1.getContent(), value1);

        Object value2 = AttributeDefinitionUtils.getAttributeContent(attribute2Name, attributes, false);
        Assertions.assertNotNull(value2);
        Assertions.assertTrue(containsAttributeDefinition(attribute2Name, attributes));
        Assertions.assertEquals(attribute2.getContent(), value2);

        Object value3 = AttributeDefinitionUtils.getAttributeContent("wrongName", attributes, false);
        Assertions.assertNull(value3);
        Assertions.assertFalse(containsAttributeDefinition("wrongName", attributes));
    }

    @Test
    public void testGetAttributeNameAndUuidContent() {
        String attribute1Name = "testAttribute1";

        HashMap<String, Object> attribute2Value = new HashMap<>();
        attribute2Value.put("uuid", UUID.randomUUID().toString());
        attribute2Value.put("name", "testName");

        List<RequestAttributeDto> attributes = AttributeDefinitionUtils.createAttributes(attribute1Name, List.of(new ObjectAttributeContent(attribute1Name, attribute2Value)));
        NameAndUuidDto dto = getNameAndUuidData(attribute1Name, attributes);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(attribute2Value.get("uuid"), dto.getUuid());
        Assertions.assertEquals(attribute2Value.get("name"), dto.getName());
    }

    @Test
    public void testGetAttributeCredentialContent() {
        String attribute1Name = "testAttribute1";
        List<RequestAttributeDto> credentialAttributes = createAttributes("credAttr", List.of(new IntegerAttributeContent(987)));

        CredentialAttributeContentData credentialDto = new CredentialAttributeContentData();
        credentialDto.setUuid(UUID.randomUUID().toString());
        credentialDto.setName("testName");
        credentialDto.setAttributes(AttributeDefinitionUtils.clientAttributeConverter(credentialAttributes));

        List<RequestAttributeDto> attributes = AttributeDefinitionUtils.createAttributes(attribute1Name, List.of(new CredentialAttributeContent("testName", credentialDto)));

        CredentialAttributeContentData dto = getCredentialContent(attribute1Name, attributes);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(credentialDto.getUuid(), dto.getUuid());
        Assertions.assertEquals(credentialDto.getName(), dto.getName());
        Assertions.assertEquals(credentialAttributes.get(0).getName(), dto.getAttributes().get(0).getName());
    }

    @Test
    public void testAttributeSerialization() {
        String attrData = "[{\"name\": \"tokenType\", \"content\": [{\"reference\": \"PEM\", \"data\": \"PEM\"}]}, " +
                "{\"name\": \"description\", \"content\": [{\"reference\": \"DEMO RA Profile\", \"data\": \"DEMO RA Profile\"}]}, " +
                "{\"name\": \"endEntityProfile\", \"content\": [{\"reference\": \"DemoTLSServerEndEntityProfile\", \"data\": {\"id\": 0, \"name\": \"DemoTLSServerEndEntityProfile\"}}]}, " +
                "{\"name\": \"certificateProfile\", \"content\": [{\"reference\": \"DemoTLSServerEECertificateProfile\", \"data\": {\"id\": 0, \"name\": \"DemoTLSServerEECertificateProfile\"}}]}, " +
                "{\"name\": \"certificationAuthority\", \"content\": [{\"reference\": \"DemoServerSubCA\", \"data\": {\"id\": 0, \"name\": \"DemoServerSubCA\"}}]}, " +
                "{\"name\": \"sendNotifications\", \"content\": [{\"reference\": \"\", \"data\": false}]}, " +
                "{\"name\": \"keyRecoverable\", \"content\": [{\"reference\": \"\", \"data\": true}]}]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);
        Assertions.assertNotNull(attrs);
        Assertions.assertEquals(7, attrs.size());

        NameAndIdDto endEntityProfile = getNameAndIdData("endEntityProfile", AttributeDefinitionUtils.getClientAttributes(attrs));
        Assertions.assertNotNull(endEntityProfile);
        Assertions.assertEquals(0, endEntityProfile.getId());
        Assertions.assertEquals("DemoTLSServerEndEntityProfile", endEntityProfile.getName());

        String serialized = serialize(attrs);
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"tokenType\".*\"content\":.*\"data\":\"PEM\".*$"));
        Assertions.assertTrue(serialized.matches("^.*\"name\":\"keyRecoverable\".*\"data\":true.*$"));
    }

    @Test
    public void testValidateAttributes_success() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        StringAttributeContent attributeContent = new StringAttributeContent("1234");

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_failNoAttribute() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                validateAttributes(List.of(definition), List.of())
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_failNoValue() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);

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
        StringAttributeContent attributeContent = new StringAttributeContent("1234");
        String validationRegex = "^\\d{4}$";

        //TODO Validation

        Assertions.assertTrue(attributeContent.getData().matches(validationRegex));

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_regexFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        StringAttributeContent attributeContent = new StringAttributeContent("12345");
        String validationRegex = "^\\d{4}$";

        RegexpAttributeConstraint constraint = new RegexpAttributeConstraint();
        constraint.setData(validationRegex);

        Assertions.assertFalse(attributeContent.getData().matches(validationRegex));

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_regexPass() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        StringAttributeContent attributeContent = new StringAttributeContent("1234");
        String validationRegex = "^\\d{4}$";

        RegexpAttributeConstraint constraint = new RegexpAttributeConstraint();
        constraint.setData(validationRegex);

        Assertions.assertTrue(attributeContent.getData().matches(validationRegex));

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_IntegerRange() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        IntegerAttributeContent attributeContent = new IntegerAttributeContent(1234);

        RangeAttributeConstraint constraint = new RangeAttributeConstraint();

        RangeAttributeConstraintData data = new RangeAttributeConstraintData();
        data.setFrom(100);
        data.setTo(2000);
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.INTEGER);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_IntegerRangeFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        IntegerAttributeContent attributeContent = new IntegerAttributeContent(2001);

        RangeAttributeConstraint constraint = new RangeAttributeConstraint();

        RangeAttributeConstraintData data = new RangeAttributeConstraintData();
        data.setFrom(100);
        data.setTo(2000);
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.INTEGER);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_IntegerRangeTypeFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        StringAttributeContent attributeContent = new StringAttributeContent("2001");

        RangeAttributeConstraint constraint = new RangeAttributeConstraint();

        RangeAttributeConstraintData data = new RangeAttributeConstraintData();
        data.setFrom(100);
        data.setTo(2000);
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_FloatRange() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        FloatAttributeContent attributeContent = new FloatAttributeContent(121.34f);

        RangeAttributeConstraint constraint = new RangeAttributeConstraint();

        RangeAttributeConstraintData data = new RangeAttributeConstraintData();
        data.setFrom(100);
        data.setTo(2000);
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.INTEGER);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_FloatRangeFail() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        FloatAttributeContent attributeContent = new FloatAttributeContent(20.01f);

        RangeAttributeConstraint constraint = new RangeAttributeConstraint();

        RangeAttributeConstraintData data = new RangeAttributeConstraintData();
        data.setFrom(100);
        data.setTo(2000);
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.INTEGER);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    public void testValidateAttributes_DateTime() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        DateTimeAttributeContent attributeContent = new DateTimeAttributeContent(ZonedDateTime.now());

        DateTimeAttributeConstraint constraint = new DateTimeAttributeConstraint();

        DateTimeAttributeConstraintData data = new DateTimeAttributeConstraintData();
        data.setFrom(LocalDateTime.now().minusMinutes(10));
        data.setTo(LocalDateTime.now().plusMinutes(10));
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.DATETIME);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_DateTimeFailure() {
        String attributeName = "testAttribute1";
        String attributeId = "9379ca2c-aa51-42c8-8afd-2a2d16c99c57";
        DateTimeAttributeContent attributeContent = new DateTimeAttributeContent(ZonedDateTime.now());

        DateTimeAttributeConstraint constraint = new DateTimeAttributeConstraint();

        DateTimeAttributeConstraintData data = new DateTimeAttributeConstraintData();
        data.setFrom(LocalDateTime.now().plusMinutes(5));
        data.setTo(LocalDateTime.now().plusMinutes(10));
        constraint.setData(data);

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setUuid(attributeId);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.DATETIME);

        DataAttributeProperties properties = new DataAttributeProperties();
        properties.setRequired(true);
        definition.setProperties(properties);
        definition.setConstraints(List.of(constraint));

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setUuid(attributeId);
        attribute.setContent(List.of(attributeContent));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }


    @Test
    public void testValidateAttributes_credentialMap() {
        String attributeName = "testAttribute1";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);

        CredentialAttributeContentData credential = new CredentialAttributeContentData();
        credential.setName("testName");
        credential.setUuid("testUuid");

        CredentialAttributeContent credentialContent = new CredentialAttributeContent("Test Credential", credential);

        attribute.setContent(List.of(credentialContent));

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialDto() {
        String attributeName = "testAttribute1";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.CREDENTIAL);

        CredentialAttributeContent credentialContent = new CredentialAttributeContent(attributeName, new CredentialAttributeContentData());

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(List.of(credentialContent));

        validateAttributes(List.of(definition), List.of(attribute));
    }

    @Test
    public void testValidateAttributes_credentialFail() {
        String attributeName = "testAttribute1";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.CREDENTIAL);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName(attributeName);
        attribute.setContent(List.of(new IntegerAttributeContent(123))); // cause or failure
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () ->
                // tested method
                validateAttributes(List.of(definition), List.of(attribute))
        );

        Assertions.assertEquals(1, exception.getErrors().size());
    }

    @Test
    @Disabled
    public void testValidateAttributes_unknownAttribute() {
        String attributeName = "testAttribute1";

        DataAttribute definition = new DataAttribute();
        definition.setName(attributeName);
        definition.setType(AttributeType.DATA);
        definition.setContentType(AttributeContentType.STRING);

        RequestAttributeDto attribute = new RequestAttributeDto();
        attribute.setName("unknown-attribute");  // cause or failure
        attribute.setContent(List.of(new StringAttributeContent("123")));

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
        callbackRequest.setPathVariable(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));

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
                AttributeType.DATA,
                AttributeContentType.CREDENTIAL,
                "toQueryParam",
                Collections.singleton(AttributeValueTarget.REQUEST_PARAMETER)));
        mappings.add(new AttributeCallbackMapping(
                "fromAttribute",
                "toBodyKey",
                AttributeValueTarget.BODY));

        AttributeCallback callback = new AttributeCallback();
        callback.setCallbackContext("core/getCredentials");
        callback.setCallbackMethod("GET");
        callback.setMappings(mappings);

        RequestAttributeCallback callbackRequest = new RequestAttributeCallback();
        callbackRequest.setPathVariable(Map.ofEntries(Map.entry("credentialKind", "softKeyStore")));
        callbackRequest.setRequestParameter(Map.ofEntries(Map.entry("toQueryParam", 1234)));


        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> validateCallback(callback, callbackRequest));

        Assertions.assertNotNull(exception.getErrors());
        Assertions.assertFalse(exception.getErrors().isEmpty());
        Assertions.assertEquals(2, exception.getErrors().size());
    }

    @Test
    public void testGetAttributeJsonContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"reference\": \"Item\",\n" +
                "      \"data\": {\n" +
                "        \"customField\": 1234,\n" +
                "        \"exists\": true,\n" +
                "        \"name\": \"testingName\"\n" +
                "      }\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        ObjectAttributeContent data = getAttributeContent("testJsonAttribute", attrs, ObjectAttributeContent.class).get(0);

        Assertions.assertEquals("Item", data.getReference());
    }

    @Test
    public void testGetAttributeStringContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": \"Item\"\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        StringAttributeContent data = getAttributeContent("testJsonAttribute", attrs, StringAttributeContent.class).get(0);

        Assertions.assertEquals("Item", data.getData());
    }

    @Test
    public void testGetAttributeInetegerContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": 1234\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        IntegerAttributeContent data = getAttributeContent("testJsonAttribute", attrs, IntegerAttributeContent.class).get(0);

        Assertions.assertEquals(1234, data.getData());
    }

    @Test
    public void testGetAttributeDateTimeContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": \"2011-12-03T10:15:30+01:00\"\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        DateTimeAttributeContent data = getAttributeContent("testJsonAttribute", attrs, DateTimeAttributeContent.class).get(0);

        String dateInString = "2011-12-03T10:15:30+01:00";
        ZonedDateTime date = ZonedDateTime.parse(dateInString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Assertions.assertEquals(date, data.getData());
    }

    @Test
    public void testGetAttributeDateContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": \"2001-07-04\"\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        DateAttributeContent data = getAttributeContent("testJsonAttribute", attrs, DateAttributeContent.class).get(0);

        String dateInString = "2001-07-04";
        LocalDate localDate = LocalDate.parse(dateInString);

        Assertions.assertEquals(localDate, data.getData());
    }

    @Test
    public void testGetAttributeTimeContent_success() throws ParseException {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testJsonAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": \"12:14:25\"\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        TimeAttributeContent data = getAttributeContent("testJsonAttribute", attrs, TimeAttributeContent.class).get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String dateInString = "12:14:25";
        LocalTime localTime = LocalTime.parse(dateInString);

        Assertions.assertEquals(localTime, data.getData());
    }

    @Test
    public void testGetStringAttributeContentValue_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testStringAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": \"Test String Value\"\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        String value = getAttributeContentValue("testStringAttribute", attrs, StringAttributeContent.class).get(0).getData();

        Assertions.assertNotNull(value);
        Assertions.assertEquals("Test String Value", value);
    }

    @Test
    public void testGetFileAttributeContentValue_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testFileAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"data\": {\"content\":\"Test File Value\"}\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        String value = getAttributeContentValue("testFileAttribute", attrs, FileAttributeContent.class).get(0).getData().getContent();

        Assertions.assertNotNull(value);
        Assertions.assertEquals("Test File Value", value);
    }

    @Test
    public void testGetCredentialAttributeContent_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testCredentialAttribute\",\n" +
                "    \"content\": [{\n" +
                "      \"reference\": \"Test Credential Value\",\n" +
                "      \"data\": {\n" +
                "        \"uuid\": \"9379ca2c-aa51-42c8-8afd-2a2d16c99c57\",\n" +
                "        \"name\": \"Test Credential\"\n" +
                "      }\n" +
                "    }]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        NameAndUuidDto data = getObjectAttributeContentData("testCredentialAttribute", attrs, NameAndUuidDto.class).get(0);

        Assertions.assertNotNull(data);
        Assertions.assertEquals(data.getClass(), NameAndUuidDto.class);
    }

    @Test
    public void testGetAttributeContentAsListOfString_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testAttributeListString\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"data\": \"string1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"data\": \"string2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"data\": \"string3\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        List<String> listString = getAttributeContentValueList("testAttributeListString", attrs, BaseAttributeContent.class);

        Assertions.assertNotNull(listString);
        Assertions.assertEquals(3, listString.size());
    }

    @Test
    public void testGetJsonAttributeContentAsListOfUuidAndName_success() {
        String attrData = "[\n" +
                "  {\n" +
                "    \"name\": \"testCredentialAttribute\",\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"reference\": \"Test Credential Value 1\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"9379ca2c-aa51-42c8-8afd-2a2d16c99c57\",\n" +
                "          \"name\": \"Test Credential 1\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"reference\": \"Test Credential Value 2\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"696a354f-55d2-4507-b454-a5a7475a7932\",\n" +
                "          \"name\": \"Test Credential 2\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        List<BaseAttribute> attrs = deserialize(attrData, BaseAttribute.class);

        List<String> listData = getObjectAttributeContentDataList("testCredentialAttribute", attrs, NameAndUuidDto.class);

        Assertions.assertNotNull(listData);
        Assertions.assertEquals(2, listData.size());
    }

    @Test
    public void testGetJsonAttributeConstraints_success() {
        String regExp = "^((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])|(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\\\-]*[a-zA-Z0-9])\\\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\\\-]*[A-Za-z0-9]))$";

        String attrData = "[\n" +
                "  {\n" +
                "    \"uuid\": \"93ca0ba2-3863-4ffa-a469-fd14ab3992bf\",\n" +
                "    \"name\": \"address\",\n" +
                "    \"type\": \"data\",\n" +
                "    \"contentType\": \"string\",\n" +
                "    \"properties\": {\n" +
                "       \"label\": \"Test Credential Value 1\",\n" +
                "       \"visible\": true,\n" +
                "       \"required\": true\n" +
                "    },\n" +
                "    \"constraints\": [\n" +
                "      {\n" +
                "       \"type\": \"regExp\",\n" +
                "       \"data\": \"" + regExp + "\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"reference\": \"Test Credential Value 1\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"9379ca2c-aa51-42c8-8afd-2a2d16c99c57\",\n" +
                "          \"name\": \"Test Credential 1\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"reference\": \"Test Credential Value 2\",\n" +
                "        \"data\": {\n" +
                "          \"uuid\": \"696a354f-55d2-4507-b454-a5a7475a7932\",\n" +
                "          \"name\": \"Test Credential 2\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]";

        List<DataAttribute> attrs = deserialize(attrData, DataAttribute.class);
        Assertions.assertEquals(1, attrs.size());
        Assertions.assertEquals(1, attrs.get(0).getConstraints().size());
        Assertions.assertEquals(AttributeConstraintType.REGEXP, attrs.get(0).getConstraints().get(0).getType());
    }
}
