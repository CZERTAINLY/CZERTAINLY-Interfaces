package com.otilm.api.model.connector.cryptography.v2.material;

import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.util.Set;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.EncryptionScheme;
import org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import org.bouncycastle.asn1.pkcs.PBES2Parameters;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEOutputEncryptorBuilder;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.otilm.api.testsupport.ConstraintViolationAssertions.assertHasViolation;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class EncryptedKeyMaterialV2DtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private static final Validator VALIDATOR = VALIDATORS.validator();

    private static final Provider BOUNCY_CASTLE = new BouncyCastleProvider();

    private static final char[] TRANSPORT_PASSPHRASE = "transport-passphrase".toCharArray();

    private static final int PINNED_ITERATIONS = 100_000;

    private static final byte[] SIXTEEN_BYTE_SALT = new byte[16];

    private static final byte[] SIXTEEN_BYTE_IV = new byte[16];

    private static final byte[] CIPHERTEXT = new byte[32];

    private static final String PBKDF2_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    private static final int DERIVED_KEY_BITS = 256;

    private static final int CIPHER_BLOCK = 16;

    private static final String MALFORMED_MESSAGE = "encryptedPrivateKeyInfo must contain a DER-encoded "
            + "PKCS#8 EncryptedPrivateKeyInfo";

    private static final String CIPHERTEXT_MESSAGE = "material must carry a non-empty ciphertext of whole 16-byte AES blocks";

    private static final String SCHEME_MESSAGE = "material must be protected with PBES2 using PBKDF2-HMAC-SHA256 and AES-256-CBC";

    private static final String PARAMETERS_MESSAGE = "material must use a salt of at least 16 bytes, "
            + "between 100000 and 10000000 iterations, a 32-byte derived key when the key length is present, "
            + "and a 16-byte initialisation vector";

    @Test
    void material_acceptsEnvelopeInThePinnedProfile() throws Exception {
        // given
        byte[] envelope = pinnedEnvelope();

        // when
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // then
        assertTrue(VALIDATOR.validate(material).isEmpty(), "pinned-profile material must not violate any constraint");
    }

    @Test
    void material_rejectsUnparseableEncoding() {
        // given
        byte[] notAnEnvelope = {1, 2, 3, 4};
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();

        material.setEncryptedPrivateKeyInfo(notAnEnvelope);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "canonicalEnvelope", MALFORMED_MESSAGE);
    }

    @Test
    void material_rejectsNonCanonicalEncoding() throws Exception {
        // given
        byte[] envelopeWithTrailingByte = withTrailingByte(pinnedEnvelope());
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();

        material.setEncryptedPrivateKeyInfo(envelopeWithTrailingByte);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "canonicalEnvelope", MALFORMED_MESSAGE);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("schemeViolations")
    void material_rejectsProtectionSchemeOutsideThePinnedProfile(byte[] envelope) {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "pinnedProtectionScheme", SCHEME_MESSAGE);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("parameterViolations")
    void material_rejectsProtectionParametersOutsideThePinnedProfile(byte[] envelope) {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "pinnedProtectionParameters", PARAMETERS_MESSAGE);
    }

    @Test
    void material_acceptsAbsentKeyLength() {
        // given
        byte[] envelope = craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                PKCSObjectIdentifiers.id_hmacWithSHA256, NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV));

        // when
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // then
        assertTrue(VALIDATOR.validate(material).isEmpty(),
                "an absent key length is implied by AES-256-CBC and must be accepted");
    }

    @Test
    void material_acceptsExplicitKeyLengthOfThirtyTwoBytes() {
        // given
        byte[] envelope = craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, 32,
                PKCSObjectIdentifiers.id_hmacWithSHA256, NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV));

        // when
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // then
        assertTrue(VALIDATOR.validate(material).isEmpty(), "an explicit 32-byte key length must be accepted");
    }

    @Test
    void material_requiresEncryptedPrivateKeyInfo() {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "encryptedPrivateKeyInfo", "encryptedPrivateKeyInfo is required");
    }

    @Test
    void material_acceptsNullToClearTheEnvelope() throws Exception {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(pinnedEnvelope());

        // when
        material.setEncryptedPrivateKeyInfo(null);

        // then
        assertNull(material.getEncryptedPrivateKeyInfo());
    }

    @Test
    void material_copiesEnvelopeOnAccess() throws Exception {
        // given
        byte[] envelope = pinnedEnvelope();
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        byte[] returned = material.getEncryptedPrivateKeyInfo();

        // then
        assertNotSame(envelope, returned, "the envelope must not be shared with the caller");
        assertArrayEquals(envelope, returned);
    }

    @Test
    void material_isolatedFromCallerMutationAfterSet() throws Exception {
        // given
        byte[] envelope = pinnedEnvelope();
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        envelope[0] = (byte) (envelope[0] ^ 0xFF);

        // then
        assertNotEquals(envelope[0], material.getEncryptedPrivateKeyInfo()[0],
                "a caller mutating its array must not change the stored envelope");
    }

    @Test
    void toString_redactsEnvelope() throws Exception {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(pinnedEnvelope());

        // when
        String rendered = material.toString();

        // then
        assertFalse(rendered.contains("encryptedPrivateKeyInfo="), "toString must not render the envelope");
    }

    static Stream<Named<byte[]>> schemeViolations() {
        return Stream
                .of(Named
                        .named("legacy PKCS#12 PBE scheme",
                                craftedEnvelope(
                                        new AlgorithmIdentifier(PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC,
                                                new PBKDF2Params(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS)))),
                        Named
                                .named("PBKDF2 with HMAC-SHA1",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                                                PKCSObjectIdentifiers.id_hmacWithSHA1,
                                                NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV))),
                        Named
                                .named("AES-128-CBC encryption",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                                                PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                NISTObjectIdentifiers.id_aes128_CBC, SIXTEEN_BYTE_IV))),
                        Named
                                .named("HMAC-SHA256 without parameters",
                                        craftedEnvelope(pbes2WithPrf(
                                                new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256)))),
                        Named
                                .named("HMAC-SHA256 with parameters that are not NULL",
                                        craftedEnvelope(pbes2WithPrf(
                                                new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                        new DEROctetString(new byte[0]))))));
    }

    static Stream<Named<byte[]>> parameterViolations() {
        return Stream
                .of(Named
                        .named("salt shorter than 16 bytes",
                                craftedEnvelope(pbes2(new byte[8], PINNED_ITERATIONS, null,
                                        PKCSObjectIdentifiers.id_hmacWithSHA256, NISTObjectIdentifiers.id_aes256_CBC,
                                        SIXTEEN_BYTE_IV))),
                        Named
                                .named("iteration count below the floor",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, 99_999, null,
                                                PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV))),
                        Named
                                .named("iteration count above the ceiling",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, 10_000_001, null,
                                                PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV))),
                        Named
                                .named("explicit key length other than 32 bytes",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, 16,
                                                PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV))),
                        Named
                                .named("initialisation vector other than 16 bytes",
                                        craftedEnvelope(pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                                                PKCSObjectIdentifiers.id_hmacWithSHA256,
                                                NISTObjectIdentifiers.id_aes256_CBC, new byte[8]))));
    }

    @Test
    void rejectsProtectionWhosePbes2ParametersAreAbsent() {
        // given
        byte[] envelope = craftedEnvelope(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2));
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "pinnedProtectionScheme", SCHEME_MESSAGE);
    }

    @Test
    void rejectsDerivedKeyLengthTooLargeForAnInt() {
        // given
        AlgorithmIdentifier protection = pbes2WithKeyLength(BigInteger.ONE.shiftLeft(64));
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(craftedEnvelope(protection));

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "pinnedProtectionParameters", PARAMETERS_MESSAGE);
    }

    @ParameterizedTest(name = "{0} ciphertext bytes")
    @ValueSource(ints = {0, 1, 15, 17, 31})
    void rejectsCiphertextThatIsNotWholeAesBlocks(int ciphertextLength) {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelopeWithCiphertext(new byte[ciphertextLength]));

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations, "wholeCipherBlocks", CIPHERTEXT_MESSAGE);
    }

    @Test
    void acceptsCiphertextOfWholeAesBlocks() {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelopeWithCiphertext(new byte[16 * 5]));

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertTrue(violations.isEmpty(), () -> "expected no violations, got " + violations);
    }

    @Test
    void acceptsAnEnvelopeThatOpensWithTheStatedParameters() throws Exception {
        // given
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        byte[] privateKeyInfo = keyPairGenerator.generateKeyPair().getPrivate().getEncoded();
        byte[] envelope = protectedEnvelope(privateKeyInfo);

        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(envelope);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertTrue(violations.isEmpty(), () -> "expected no violations, got " + violations);
        assertArrayEquals(privateKeyInfo, openWithPassphrase(material.getEncryptedPrivateKeyInfo()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("protectionBlocksMissingAField")
    void rejectsProtectionWithAMissingField(AlgorithmIdentifier protection, String expectedMessage) {
        // given
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(craftedEnvelope(protection));

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertHasViolation(violations,
                expectedMessage.equals(SCHEME_MESSAGE) ? "pinnedProtectionScheme" : "pinnedProtectionParameters",
                expectedMessage);
    }

    static Stream<Arguments> protectionBlocksMissingAField() {
        AlgorithmIdentifier pinnedScheme = new AlgorithmIdentifier(NISTObjectIdentifiers.id_aes256_CBC,
                new DEROctetString(SIXTEEN_BYTE_IV));
        return Stream
                .of(arguments(
                        named("key derivation without parameters",
                                pbes2Of(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2), pinnedScheme)),
                        SCHEME_MESSAGE),
                        arguments(
                                named("key derivation without an iteration count",
                                        pbes2Of(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2,
                                                new DERSequence(new DEROctetString(SIXTEEN_BYTE_SALT))), pinnedScheme)),
                                SCHEME_MESSAGE),
                        arguments(
                                named("encryption scheme without an initialisation vector",
                                        pbes2Of(pinnedKeyDerivation(),
                                                new AlgorithmIdentifier(NISTObjectIdentifiers.id_aes256_CBC))),
                                PARAMETERS_MESSAGE));
    }

    @Test
    void rejectsAnEnvelopeLongerThanTheContractAccepts() {
        // given
        byte[] oversized = envelopeAcross(EncryptedKeyMaterialV2Dto.MAXIMUM_ENVELOPE_LENGTH, false);
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(oversized);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertTrue(
                oversized.length > EncryptedKeyMaterialV2Dto.MAXIMUM_ENVELOPE_LENGTH
                        && oversized.length <= EncryptedKeyMaterialV2Dto.MAXIMUM_ENVELOPE_LENGTH + CIPHER_BLOCK,
                () -> "the fixture must be the first envelope past the maximum, was " + oversized.length);
        assertHasViolation(violations, "withinMaximumLength", "encryptedPrivateKeyInfo must not exceed 65536 bytes");
    }

    @Test
    void acceptsAnEnvelopeAsLongAsTheContractAllows() {
        // given
        byte[] largest = envelopeAcross(EncryptedKeyMaterialV2Dto.MAXIMUM_ENVELOPE_LENGTH, true);
        EncryptedKeyMaterialV2Dto material = new EncryptedKeyMaterialV2Dto();
        material.setEncryptedPrivateKeyInfo(largest);

        // when
        Set<ConstraintViolation<EncryptedKeyMaterialV2Dto>> violations = VALIDATOR.validate(material);

        // then
        assertTrue(largest.length > EncryptedKeyMaterialV2Dto.MAXIMUM_ENVELOPE_LENGTH - CIPHER_BLOCK,
                () -> "the fixture must be the last envelope within the maximum, was " + largest.length);
        assertTrue(violations.isEmpty(), () -> "expected no violations, got " + violations);
    }

    private static AlgorithmIdentifier pbes2Of(ASN1Encodable keyDerivation, ASN1Encodable encryptionScheme) {
        return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2,
                new DERSequence(new ASN1Encodable[]{keyDerivation, encryptionScheme}));
    }

    private static AlgorithmIdentifier pinnedKeyDerivation() {
        PBKDF2Params derivation = new PBKDF2Params(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS,
                new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256, DERNull.INSTANCE));
        return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2, derivation);
    }

    private static AlgorithmIdentifier pbes2WithKeyLength(BigInteger keyLength) {
        ASN1EncodableVector derivationFields = new ASN1EncodableVector();
        derivationFields.add(new DEROctetString(SIXTEEN_BYTE_SALT));
        derivationFields.add(new ASN1Integer(PINNED_ITERATIONS));
        derivationFields.add(new ASN1Integer(keyLength));
        derivationFields.add(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256, DERNull.INSTANCE));
        KeyDerivationFunc keyDerivation = new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2,
                PBKDF2Params.getInstance(new DERSequence(derivationFields)));
        EncryptionScheme scheme = new EncryptionScheme(NISTObjectIdentifiers.id_aes256_CBC,
                new DEROctetString(SIXTEEN_BYTE_IV));
        return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, new PBES2Parameters(keyDerivation, scheme));
    }

    /**
     * The largest valid envelope that still fits within {@code limit}, and the smallest that does not. No envelope
     * lands on an arbitrary total, so the two either side of the limit are found by stepping one block at a time.
     */
    private static byte[] envelopeAcross(int limit, boolean within) {
        byte[] fitting = null;
        for (int ciphertext = CIPHER_BLOCK; ciphertext <= limit; ciphertext += CIPHER_BLOCK) {
            byte[] envelope = envelopeWithCiphertext(new byte[ciphertext]);
            if (envelope.length > limit) {
                return within ? fitting : envelope;
            }
            fitting = envelope;
        }
        throw new IllegalStateException("no envelope crosses " + limit + " bytes");
    }

    private static byte[] envelopeWithCiphertext(byte[] ciphertext) {
        AlgorithmIdentifier protection = pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                PKCSObjectIdentifiers.id_hmacWithSHA256, NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV);
        try {
            return new EncryptedPrivateKeyInfo(protection, ciphertext).getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new IllegalStateException("could not encode the crafted envelope", e);
        }
    }

    private static byte[] protectedEnvelope(byte[] privateKeyInfo) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC);
        cipher
                .init(Cipher.ENCRYPT_MODE, derivedKey(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS),
                        new IvParameterSpec(SIXTEEN_BYTE_IV));
        AlgorithmIdentifier protection = pbes2(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, null,
                PKCSObjectIdentifiers.id_hmacWithSHA256, NISTObjectIdentifiers.id_aes256_CBC, SIXTEEN_BYTE_IV);
        return new EncryptedPrivateKeyInfo(protection, cipher.doFinal(privateKeyInfo)).getEncoded(ASN1Encoding.DER);
    }

    /**
     * Opens an envelope using only what the envelope itself states, so the test proves the protection parameters travel
     * with the material rather than being agreed out of band. Every declared algorithm is checked rather than assumed:
     * a mislabelled envelope fails here instead of quietly opening with the algorithms the test had in mind.
     */
    private static byte[] openWithPassphrase(byte[] envelope) throws Exception {
        EncryptedPrivateKeyInfo parsed = EncryptedPrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(envelope));
        PBES2Parameters protection = PBES2Parameters.getInstance(parsed.getEncryptionAlgorithm().getParameters());
        PBKDF2Params derivation = PBKDF2Params.getInstance(protection.getKeyDerivationFunc().getParameters());
        byte[] iv = ASN1OctetString.getInstance(protection.getEncryptionScheme().getParameters()).getOctets();

        assertEquals(PKCSObjectIdentifiers.id_PBES2, parsed.getEncryptionAlgorithm().getAlgorithm());
        assertEquals(PKCSObjectIdentifiers.id_PBKDF2, protection.getKeyDerivationFunc().getAlgorithm());
        assertEquals(PKCSObjectIdentifiers.id_hmacWithSHA256, derivation.getPrf().getAlgorithm());
        assertEquals(NISTObjectIdentifiers.id_aes256_CBC, protection.getEncryptionScheme().getAlgorithm());
        assertEquals(EncryptedKeyMaterialV2Dto.INITIALISATION_VECTOR_LENGTH, iv.length);
        if (derivation.getKeyLength() != null) {
            assertEquals(BigInteger.valueOf(EncryptedKeyMaterialV2Dto.DERIVED_KEY_LENGTH), derivation.getKeyLength());
        }

        Cipher cipher = Cipher.getInstance(AES_CBC);
        cipher
                .init(Cipher.DECRYPT_MODE,
                        derivedKey(derivation.getSalt(), derivation.getIterationCount().intValueExact()),
                        new IvParameterSpec(iv));
        return cipher.doFinal(parsed.getEncryptedData());
    }

    private static SecretKey derivedKey(byte[] salt, int iterations) throws Exception {
        byte[] derived = SecretKeyFactory
                .getInstance(PBKDF2_HMAC_SHA256)
                .generateSecret(new PBEKeySpec(TRANSPORT_PASSPHRASE, salt, iterations, DERIVED_KEY_BITS))
                .getEncoded();
        return new SecretKeySpec(derived, "AES");
    }

    private static byte[] pinnedEnvelope() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(keyPair.getPrivate().getEncoded());
        return new PKCS8EncryptedPrivateKeyInfoBuilder(privateKeyInfo)
                .build(new JcePKCSPBEOutputEncryptorBuilder(NISTObjectIdentifiers.id_aes256_CBC)
                        .setProvider(BOUNCY_CASTLE)
                        .setPRF(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA256, DERNull.INSTANCE))
                        .setIterationCount(PINNED_ITERATIONS)
                        .build(TRANSPORT_PASSPHRASE))
                .getEncoded();
    }

    private static AlgorithmIdentifier pbes2(byte[] salt, int iterations, Integer keyLength, ASN1ObjectIdentifier prf,
            ASN1ObjectIdentifier cipher, byte[] iv) {
        AlgorithmIdentifier prfAlgorithm = new AlgorithmIdentifier(prf, DERNull.INSTANCE);
        PBKDF2Params derivation = keyLength == null
                ? new PBKDF2Params(salt, iterations, prfAlgorithm)
                : new PBKDF2Params(salt, iterations, keyLength, prfAlgorithm);
        KeyDerivationFunc keyDerivation = new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2, derivation);
        EncryptionScheme scheme = new EncryptionScheme(cipher, new DEROctetString(iv));
        return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, new PBES2Parameters(keyDerivation, scheme));
    }

    /** The pinned profile with the key derivation's pseudo-random function stated as given. */
    private static AlgorithmIdentifier pbes2WithPrf(AlgorithmIdentifier prf) {
        KeyDerivationFunc keyDerivation = new KeyDerivationFunc(PKCSObjectIdentifiers.id_PBKDF2,
                new PBKDF2Params(SIXTEEN_BYTE_SALT, PINNED_ITERATIONS, prf));
        EncryptionScheme scheme = new EncryptionScheme(NISTObjectIdentifiers.id_aes256_CBC,
                new DEROctetString(SIXTEEN_BYTE_IV));
        return new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBES2, new PBES2Parameters(keyDerivation, scheme));
    }

    private static byte[] craftedEnvelope(AlgorithmIdentifier protection) {
        try {
            return new EncryptedPrivateKeyInfo(protection, CIPHERTEXT).getEncoded(ASN1Encoding.DER);
        } catch (IOException e) {
            throw new IllegalStateException("could not encode the crafted envelope", e);
        }
    }

    private static byte[] withTrailingByte(byte[] envelope) {
        byte[] extended = new byte[envelope.length + 1];
        System.arraycopy(envelope, 0, extended, 0, envelope.length);
        return extended;
    }
}
