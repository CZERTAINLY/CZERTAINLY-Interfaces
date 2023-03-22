package com.czertainly.api.model.core.scep;

import lombok.Data;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.cms.CMSSignedGenerator;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.*;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Data
public class ScepRequestMessage {
    private final byte[] scepRequestMessage;
    private PKCS10CertificationRequest pkcs10;
    private int messageType = 0;
    private String senderNonce = null;
    private String transactionId = null;
    private byte[] requestKeyInfo = null;
    private transient SignedData signedData = null;
    private transient EnvelopedData envelopedData = null;
    private transient ContentInfo encryptedData = null;
    private transient String preferredDigestAlg = CMSSignedGenerator.DIGEST_SHA256;
    private transient String originalDigestAlgorithm = CMSSignedGenerator.DIGEST_SHA256;
    private transient X509Certificate signercert;


    public ScepRequestMessage(byte[] msg) {
        this.scepRequestMessage = msg;
    }

    /**
     * Recreate a request message from an encoded
     */
    public static ScepRequestMessage instance(final String encoded) {
        byte[] bytes = Base64.getDecoder().decode(encoded.getBytes());
        try {
            final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            final ScepRequestMessage result = (ScepRequestMessage) ois.readObject();
            ois.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not decode encoded ScepRequestMessage", e);
        }

    }

    public String getEncoded() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            throw new IllegalStateException("Could not encode ScepRequestMessage", e);
        }
        byte[] byteArray = baos.toByteArray();
        return new String(Base64.getEncoder().encode(byteArray));
    }
}