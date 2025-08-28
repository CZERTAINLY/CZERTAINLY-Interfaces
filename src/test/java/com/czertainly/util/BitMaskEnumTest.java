package com.czertainly.util;

import com.czertainly.api.model.common.enums.BitMaskEnum;
import com.czertainly.api.model.core.certificate.CertificateKeyUsage;
import com.czertainly.api.model.core.cryptography.key.KeyUsage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class BitMaskEnumTest {

    @Test
    void testKeyUsage() {
        Set<KeyUsage> keyUsages = Set.of(KeyUsage.SIGN, KeyUsage.WRAP, KeyUsage.UNWRAP);
        int bitMask = BitMaskEnum.convertSetToBitMask(keyUsages);
        Assertions.assertEquals(keyUsages, KeyUsage.convertBitMaskToSet(bitMask));
        keyUsages = Set.of();
        bitMask = BitMaskEnum.convertSetToBitMask(keyUsages);
        Assertions.assertEquals(keyUsages, KeyUsage.convertBitMaskToSet(bitMask));
    }

    @Test
    void testCertificateKeyUsage() {
        Set<CertificateKeyUsage> keyUsages = Set.of(CertificateKeyUsage.KEY_AGREEMENT, CertificateKeyUsage.KEY_ENCIPHERMENT, CertificateKeyUsage.KEY_CERT_SIGN);
        int bitMask = BitMaskEnum.convertSetToBitMask(keyUsages);
        Assertions.assertEquals(keyUsages, CertificateKeyUsage.convertBitMaskToList(bitMask));
    }

}
