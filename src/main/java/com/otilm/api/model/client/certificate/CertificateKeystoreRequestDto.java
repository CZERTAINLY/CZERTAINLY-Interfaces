package com.otilm.api.model.client.certificate;

import com.otilm.api.model.client.cryptography.key.ExportPassphraseRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

/**
 * Body of a keystore download.
 *
 * <p>
 * This is a request type of its own rather than another certificate format, so the existing certificate content
 * download can never become a way to obtain key material.
 * </p>
 */
@ToString(callSuper = true)
@Schema(name = "CertificateKeystoreRequestDto",
        description = "Passphrase and provider options for downloading a certificate with its private key")
public class CertificateKeystoreRequestDto extends ExportPassphraseRequestDto {
}
