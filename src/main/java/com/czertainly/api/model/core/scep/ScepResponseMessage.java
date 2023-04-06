package com.czertainly.api.model.core.scep;

import lombok.Data;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;

@Data
public class ScepResponseMessage {

    private final byte[] responseMessage = null;
    private PkiStatus status = PkiStatus.SUCCESS;
    private FailInfo failInfo = FailInfo.BAD_REQUEST;
    private String failText = null;
    private String senderNonce = null;
    private String recipientNonce = null;
    private String transactionId = null;
    private byte[] recipientKeyInfo = null;
    private transient X509Certificate certificate = null;
    private transient Collection<Certificate> signCertificateChain = null;
    private transient Certificate caCertificate = null;
    private transient boolean includeCaCertificate = true;
    private transient String digestAlgorithm = CMSSignedDataGenerator.DIGEST_MD5;
    private transient String provider = BouncyCastleProvider.PROVIDER_NAME;
}