package com.otilm.api.model.core.oid.properties;

import com.otilm.api.model.core.oid.ExtensionValueEncoding;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateExtensionOidPropertiesDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void valueSchemaOnDerEncodingIsValid() {
        CertificateExtensionOidPropertiesDto dto = dto(ExtensionValueEncoding.DER, "{\"type\":\"object\"}");
        assertTrue(VALIDATOR.validate(dto).isEmpty());
    }

    @Test
    void valueSchemaOnStringEncodingIsRejected() {
        CertificateExtensionOidPropertiesDto dto = dto(ExtensionValueEncoding.UTF8_STRING, "{\"type\":\"object\"}");
        assertFalse(VALIDATOR.validate(dto).isEmpty());
    }

    @Test
    void noValueSchemaIsValidOnAnyEncoding() {
        assertTrue(VALIDATOR.validate(dto(ExtensionValueEncoding.UTF8_STRING, null)).isEmpty());
        assertTrue(VALIDATOR.validate(dto(ExtensionValueEncoding.DER, null)).isEmpty());
    }

    private static CertificateExtensionOidPropertiesDto dto(ExtensionValueEncoding encoding, String schema) {
        CertificateExtensionOidPropertiesDto dto = new CertificateExtensionOidPropertiesDto();
        dto.setDefaultCritical(false);
        dto.setValueEncoding(encoding);
        dto.setValueSchema(schema);
        return dto;
    }
}
