package com.czertainly.core.util;

import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class CertificateUtil {

    public static X509Certificate getX509Certificate(byte[] decoded) throws CertificateException {
        try {
            X509Certificate certificate = (X509Certificate) new CertificateFactory().engineGenerateCertificate(new ByteArrayInputStream(decoded));
            if (certificate.getPublicKey() == null) {
                java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("x.509");
                return (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(decoded));
            }
            return certificate;
        } catch (Exception e) {
            X509Certificate certificate = (X509Certificate) new CertificateFactory().engineGenerateCertificates(new ByteArrayInputStream(decoded)).iterator().next();
            if (certificate.getPublicKey() == null) {
                java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("x.509");
                return (X509Certificate) certificateFactory.generateCertificates(new ByteArrayInputStream(decoded)).iterator().next();
            }
            return certificate;
        }
    }

    public static X509Certificate parseCertificate(String cert) throws CertificateException {
        cert = cert.replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "")
                .replace("\r", "").replace("\n", "");
        byte[] decoded = Base64.getDecoder().decode(cert);
        return getX509Certificate(decoded);
    }

}
