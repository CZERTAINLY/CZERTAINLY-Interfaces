package com.otilm.api.model.core.oid.properties;

import com.otilm.api.model.core.oid.ExtensionValueEncoding;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateExtensionOidPropertiesDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void valueSchemaOnDerEncodingIsValid() {
        CertificateExtensionOidPropertiesDto dto = dto(ExtensionValueEncoding.DER, "{\"type\":\"object\"}");
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void valueSchemaOnStringEncodingIsRejected() {
        CertificateExtensionOidPropertiesDto dto = dto(ExtensionValueEncoding.UTF8_STRING, "{\"type\":\"object\"}");
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void noValueSchemaIsValidOnAnyEncoding() {
        assertTrue(validator.validate(dto(ExtensionValueEncoding.UTF8_STRING, null)).isEmpty());
        assertTrue(validator.validate(dto(ExtensionValueEncoding.DER, null)).isEmpty());
    }

    private static CertificateExtensionOidPropertiesDto dto(ExtensionValueEncoding encoding, String schema) {
        CertificateExtensionOidPropertiesDto dto = new CertificateExtensionOidPropertiesDto();
        dto.setDefaultCritical(false);
        dto.setValueEncoding(encoding);
        dto.setValueSchema(schema);
        return dto;
    }
}
