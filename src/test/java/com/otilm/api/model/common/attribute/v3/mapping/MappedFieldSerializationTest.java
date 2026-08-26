package com.otilm.api.model.common.attribute.v3.mapping;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.otilm.api.model.core.certificate.GeneralNameType;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MappedFieldSerializationTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    // -------------------------------------------------------------------------
    // RdnMappedField
    // -------------------------------------------------------------------------

    @Test
    void rdnMappedField_serializesDiscriminator() throws Exception {
        RdnMappedField field = new RdnMappedField();
        field.setFieldType(FieldType.RDN);
        field.setRdn("CN");

        JsonNode json = mapper.valueToTree(field);

        assertEquals(FieldType.Codes.RDN, json.get("fieldType").asText());
        assertEquals("CN", json.get("rdn").asText());
    }

    @Test
    void rdnMappedField_deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "fieldType": "rdn",
                  "rdn": "CN",
                  "order": 1
                }
                """;

        MappedField base = mapper.readValue(json, MappedField.class);

        assertInstanceOf(RdnMappedField.class, base);
        RdnMappedField result = (RdnMappedField) base;
        assertEquals(FieldType.RDN, result.getFieldType());
        assertEquals("CN", result.getRdn());
        assertEquals(1, result.getOrder());
    }

    @Test
    void rdnMappedField_roundTrip() throws Exception {
        RdnMappedField original = new RdnMappedField();
        original.setFieldType(FieldType.RDN);
        original.setRdn("CN");
        original.setOrder(1);
        original.setSource(FieldSource.CSR);

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(RdnMappedField.class, deserialized);
        RdnMappedField result = (RdnMappedField) deserialized;
        assertEquals(FieldType.RDN, result.getFieldType());
        assertEquals("CN", result.getRdn());
        assertEquals(1, result.getOrder());
        assertEquals(FieldSource.CSR, result.getSource());
    }

    // -------------------------------------------------------------------------
    // SanMappedField
    // -------------------------------------------------------------------------

    @Test
    void sanMappedField_serializesDiscriminator() throws Exception {
        SanMappedField field = new SanMappedField();
        field.setFieldType(FieldType.SAN);
        field.setGeneralNameType(GeneralNameType.DNS);

        JsonNode json = mapper.valueToTree(field);

        assertEquals(FieldType.Codes.SAN, json.get("fieldType").asText());
    }

    @Test
    void sanMappedField_deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "fieldType": "san",
                  "generalNameType": "dns",
                  "order": 1,
                  "source": "csr"
                }
                """;

        MappedField base = mapper.readValue(json, MappedField.class);

        assertInstanceOf(SanMappedField.class, base);
        SanMappedField result = (SanMappedField) base;
        assertEquals(FieldType.SAN, result.getFieldType());
        assertEquals(GeneralNameType.DNS, result.getGeneralNameType());
        assertEquals(1, result.getOrder());
        assertEquals(FieldSource.CSR, result.getSource());
    }

    @Test
    void sanMappedField_roundTrip() throws Exception {
        SanMappedField original = new SanMappedField();
        original.setFieldType(FieldType.SAN);
        original.setGeneralNameType(GeneralNameType.DNS);
        original.setOrder(1);
        original.setSource(FieldSource.CSR);

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(SanMappedField.class, deserialized);
        SanMappedField result = (SanMappedField) deserialized;
        assertEquals(FieldType.SAN, result.getFieldType());
        assertEquals(GeneralNameType.DNS, result.getGeneralNameType());
        assertEquals(1, result.getOrder());
        assertEquals(FieldSource.CSR, result.getSource());
    }

    @Test
    void sanMappedField_otherName_roundTrip() throws Exception {
        SanMappedField original = new SanMappedField();
        original.setFieldType(FieldType.SAN);
        original.setGeneralNameType(GeneralNameType.OTHER_NAME);
        original.setOtherNameOid("1.3.6.1.4.1.311.20.2.3");

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(SanMappedField.class, deserialized);
        SanMappedField result = (SanMappedField) deserialized;
        assertEquals(GeneralNameType.OTHER_NAME, result.getGeneralNameType());
        assertEquals("1.3.6.1.4.1.311.20.2.3", result.getOtherNameOid());
    }

    // -------------------------------------------------------------------------
    // ExtensionMappedField
    // -------------------------------------------------------------------------

    @Test
    void extensionMappedField_serializesDiscriminator() throws Exception {
        ExtensionMappedField field = new ExtensionMappedField();
        field.setFieldType(FieldType.EXTENSION);
        field.setExtensionOid("2.5.29.37");

        JsonNode json = mapper.valueToTree(field);

        assertEquals(FieldType.Codes.EXTENSION, json.get("fieldType").asText());
        assertEquals("2.5.29.37", json.get("extensionOid").asText());
    }

    @Test
    void extensionMappedField_deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "fieldType": "extension",
                  "extensionOid": "2.5.29.37",
                  "criticalOverridable": true
                }
                """;

        MappedField base = mapper.readValue(json, MappedField.class);

        assertInstanceOf(ExtensionMappedField.class, base);
        ExtensionMappedField result = (ExtensionMappedField) base;
        assertEquals(FieldType.EXTENSION, result.getFieldType());
        assertEquals("2.5.29.37", result.getExtensionOid());
        assertTrue(result.isCriticalOverridable());
    }

    @Test
    void extensionMappedField_roundTrip() throws Exception {
        ExtensionMappedField original = new ExtensionMappedField();
        original.setFieldType(FieldType.EXTENSION);
        original.setExtensionOid("2.5.29.37");
        original.setCriticalOverridable(true);
        original.setOrder(1);

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(ExtensionMappedField.class, deserialized);
        ExtensionMappedField result = (ExtensionMappedField) deserialized;
        assertEquals(FieldType.EXTENSION, result.getFieldType());
        assertEquals("2.5.29.37", result.getExtensionOid());
        assertTrue(result.isCriticalOverridable());
        assertEquals(1, result.getOrder());
    }

    // -------------------------------------------------------------------------
    // KeyUsageMappedField
    // -------------------------------------------------------------------------

    @Test
    void keyUsageMappedField_serializesDiscriminator() {
        KeyUsageMappedField field = new KeyUsageMappedField();
        field.setFieldType(FieldType.KEY_USAGE);

        JsonNode json = mapper.valueToTree(field);

        assertEquals(FieldType.Codes.KEY_USAGE, json.get("fieldType").asText());
    }

    @Test
    void keyUsageMappedField_deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "fieldType": "keyUsage",
                  "order": 2,
                  "source": "csr"
                }
                """;

        MappedField base = mapper.readValue(json, MappedField.class);

        assertInstanceOf(KeyUsageMappedField.class, base);
        assertEquals(FieldType.KEY_USAGE, base.getFieldType());
        assertEquals(2, base.getOrder());
        assertEquals(FieldSource.CSR, base.getSource());
    }

    @Test
    void keyUsageMappedField_roundTrip() throws Exception {
        KeyUsageMappedField original = new KeyUsageMappedField();
        original.setFieldType(FieldType.KEY_USAGE);
        original.setOrder(2);

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(KeyUsageMappedField.class, deserialized);
        assertEquals(FieldType.KEY_USAGE, deserialized.getFieldType());
        assertEquals(2, deserialized.getOrder());
    }

    // -------------------------------------------------------------------------
    // ExtendedKeyUsageMappedField
    // -------------------------------------------------------------------------

    @Test
    void extendedKeyUsageMappedField_serializesDiscriminator() {
        ExtendedKeyUsageMappedField field = new ExtendedKeyUsageMappedField();
        field.setFieldType(FieldType.EXTENDED_KEY_USAGE);

        JsonNode json = mapper.valueToTree(field);

        assertEquals(FieldType.Codes.EXTENDED_KEY_USAGE, json.get("fieldType").asText());
    }

    @Test
    void extendedKeyUsageMappedField_deserializesViaBaseClass() throws Exception {
        String json = """
                {
                  "fieldType": "extendedKeyUsage"
                }
                """;

        MappedField base = mapper.readValue(json, MappedField.class);

        assertInstanceOf(ExtendedKeyUsageMappedField.class, base);
        assertEquals(FieldType.EXTENDED_KEY_USAGE, base.getFieldType());
    }

    @Test
    void extendedKeyUsageMappedField_roundTrip() throws Exception {
        ExtendedKeyUsageMappedField original = new ExtendedKeyUsageMappedField();
        original.setFieldType(FieldType.EXTENDED_KEY_USAGE);
        original.setSource(FieldSource.CSR);

        String json = mapper.writeValueAsString(original);
        MappedField deserialized = mapper.readValue(json, MappedField.class);

        assertInstanceOf(ExtendedKeyUsageMappedField.class, deserialized);
        assertEquals(FieldType.EXTENDED_KEY_USAGE, deserialized.getFieldType());
        assertEquals(FieldSource.CSR, deserialized.getSource());
    }

    @Test
    void everyFieldTypeCodeHasARegisteredSubtype() {
        JsonSubTypes subTypes = MappedField.class.getAnnotation(JsonSubTypes.class);
        Set<String> registered = Arrays
                .stream(subTypes.value())
                .map(JsonSubTypes.Type::name)
                .collect(Collectors.toSet());

        for (FieldType fieldType : FieldType.values()) {
            assertTrue(registered.contains(fieldType.getCode()),
                    "no MappedField subtype registered for fieldType " + fieldType.getCode());
        }
    }

    // -------------------------------------------------------------------------
    // Unknown / missing fieldType guards
    // -------------------------------------------------------------------------

    @Test
    void unknownFieldType_throwsOnDeserialization() {
        String json = """
                {
                  "fieldType": "unknown_field_type"
                }
                """;

        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, MappedField.class));
    }

    @Test
    void missingFieldType_throwsOnDeserialization() {
        String json = """
                {
                  "rdn": "CN"
                }
                """;

        assertThrows(InvalidTypeIdException.class, () -> mapper.readValue(json, MappedField.class));
    }
}
