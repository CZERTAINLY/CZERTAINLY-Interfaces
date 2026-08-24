package com.otilm.api.model.common.attribute.v3.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Maps an attribute to the Extended Key Usage extension (2.5.29.37).
 *
 * <p>
 * The target carries no properties: the extension OID is fixed, its criticality comes from the OID registry, and the
 * purposes are the attribute's content, each resolving against the OIDs registered under
 * {@link com.otilm.api.model.core.oid.OidCategory#EXTENDED_KEY_USAGE}. The attribute must be a list.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Maps an attribute to the Extended Key Usage extension; the permitted purposes are the "
        + "attribute's content, resolved against the EXTENDED_KEY_USAGE OID registry")
public class ExtendedKeyUsageMappedField extends MappedField {
}
