package com.otilm.api.model.common.attribute.v3.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Maps an attribute to the Key Usage extension.
 *
 * <p>
 * The target carries no properties: the OID is fixed at 2.5.29.15, the platform marks the extension critical - which
 * RFC 5280 &sect;4.2.1.3 recommends (SHOULD) rather than requires - and the attribute must be a list of
 * {@link com.otilm.api.model.core.certificate.CertificateKeyUsage}.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Maps an attribute to the Key Usage extension; the permitted bits are the attribute's "
        + "content, taken from CertificateKeyUsage")
public class KeyUsageMappedField extends MappedField {
}
