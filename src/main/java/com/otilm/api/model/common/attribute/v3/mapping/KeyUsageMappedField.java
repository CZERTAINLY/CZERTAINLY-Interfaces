package com.otilm.api.model.common.attribute.v3.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Maps an attribute to the Key Usage extension (2.5.29.15).
 *
 * <p>
 * The target carries no properties: the extension OID is fixed, its criticality is forced by RFC 5280 &sect;4.2.1.3,
 * and the bits are the attribute's content, taken from
 * {@link com.otilm.api.model.core.certificate.CertificateKeyUsage}. The attribute must be a list.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Maps an attribute to the Key Usage extension; the permitted bits are the attribute's "
        + "content, taken from CertificateKeyUsage")
public class KeyUsageMappedField extends MappedField {
}
