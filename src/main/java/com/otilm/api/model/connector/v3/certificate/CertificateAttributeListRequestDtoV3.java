package com.otilm.api.model.connector.v3.certificate;

import com.otilm.api.model.connector.v3.AuthorityV3ScopedRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body for the six v3 attribute-schema-listing endpoints: {@code /request/attributes}, {@code /issue/attributes},
 * {@code /renew/attributes}, {@code /revoke/attributes}, {@code /register/attributes} and {@code /identify/attributes}.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Authority + RA-profile context for listing the dynamic-attribute schema.")
public class CertificateAttributeListRequestDtoV3 extends AuthorityV3ScopedRequestDto {
}
