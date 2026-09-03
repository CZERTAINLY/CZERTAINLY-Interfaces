package com.otilm.api.model.client.cryptography.key;

import com.otilm.api.model.core.secret.Passphrase;
import com.otilm.api.model.core.secret.UploadedFile;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.otilm.api.testsupport.ConstraintViolationAssertions.assertHasViolation;
import static com.otilm.api.testsupport.ConstraintViolationAssertions.assertNoViolations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Named.named;

class KeyTransferRequestValidationTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();

    private static final Validator VALIDATOR = VALIDATORS.validator();

    private static final String LONG_ENOUGH = "correct horse battery staple";

    private static final String TOO_SHORT_MESSAGE = "passphrase must contain at least 12 characters and must not be "
            + "only whitespace";

    @Test
    void exportRequest_hasNoViolations_whenThePassphraseIsLongEnough() {
        // given
        KeyExportRequestDto request = exportRequest(LONG_ENOUGH);

        // when
        Set<ConstraintViolation<KeyExportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    @Test
    void exportRequest_requiresAPassphrase() {
        // given
        KeyExportRequestDto request = new KeyExportRequestDto();

        // when
        Set<ConstraintViolation<KeyExportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "passphrase", "passphrase is required");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("passphrasesBelowThePolicy")
    void exportRequest_rejectsAPassphraseBelowThePolicy(String passphrase) {
        // given
        KeyExportRequestDto request = exportRequest(passphrase);

        // when
        Set<ConstraintViolation<KeyExportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "passphraseAcceptable", TOO_SHORT_MESSAGE);
    }

    static Stream<Named<String>> passphrasesBelowThePolicy() {
        return Stream
                .of(named("too few characters", "short"), named("only whitespace", "               "),
                        named("twelve characters but six code points", "😀😃😄😁😆😅"));
    }

    /**
     * The passphrase is encoded in normalization form C when it protects the file. A decomposed value is longer as sent
     * than as used, so it must be refused rather than counted.
     */
    @Test
    void exportRequest_rejectsAPassphraseNotInNormalizationFormC() {
        // given
        KeyExportRequestDto request = exportRequest("cafe\u0301 cafe\u0301 cafe\u0301 cafe\u0301");

        // when
        Set<ConstraintViolation<KeyExportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "passphraseNormalized", "passphrase must be in Unicode normalization form C");
    }

    @Test
    void exportRequest_acceptsAccentedCharactersInNormalizationFormC() {
        // given
        KeyExportRequestDto request = exportRequest("caf\u00e9 caf\u00e9 caf\u00e9 caf\u00e9");

        // when
        Set<ConstraintViolation<KeyExportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    @Test
    void exportRequest_toStringRedactsThePassphrase() {
        // given
        KeyExportRequestDto request = exportRequest(LONG_ENOUGH);

        // when
        String rendered = request.toString();

        // then
        assertFalse(rendered.contains(LONG_ENOUGH), () -> "passphrase leaked into " + rendered);
    }

    @Test
    void importRequest_requiresANameAndAFile() {
        // given
        KeyImportRequestDto request = new KeyImportRequestDto();

        // when
        Set<ConstraintViolation<KeyImportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "name", "name is required");
        assertHasViolation(violations, "file", "file is required");
    }

    @Test
    void importRequest_acceptsAFileWithNoPassphraseOfItsOwn() {
        // given
        KeyImportRequestDto request = importRequest(new byte[64]);

        // when
        Set<ConstraintViolation<KeyImportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    /** The limit bounds the work a body can demand before anything is parsed; it is the platform's file limit. */
    @Test
    void importRequest_refusesAFileOverTheLimit() {
        // given
        KeyImportRequestDto request = importRequest(new byte[UploadedFile.MAXIMUM_LENGTH + 1]);

        // when
        Set<ConstraintViolation<KeyImportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertHasViolation(violations, "fileWithinLimit", "file must not exceed 5242880 bytes");
    }

    @Test
    void importRequest_acceptsAFileAtTheLimit() {
        // given
        KeyImportRequestDto request = importRequest(new byte[UploadedFile.MAXIMUM_LENGTH]);

        // when
        Set<ConstraintViolation<KeyImportRequestDto>> violations = VALIDATOR.validate(request);

        // then
        assertNoViolations(violations);
    }

    @Test
    void importRequest_toStringRedactsTheInputPassphraseAndTheFile() {
        // given
        KeyImportRequestDto request = importRequest("-----BEGIN PRIVATE KEY-----".getBytes(StandardCharsets.US_ASCII));
        request.setInputPassphrase(new Passphrase(LONG_ENOUGH.toCharArray()));

        // when
        String rendered = request.toString();

        // then
        assertFalse(rendered.contains(LONG_ENOUGH), () -> "passphrase leaked into " + rendered);
        assertFalse(rendered.contains("PRIVATE KEY"), () -> "file content leaked into " + rendered);
    }

    private static KeyImportRequestDto importRequest(byte[] content) {
        KeyImportRequestDto request = new KeyImportRequestDto();
        request.setName("imported key");
        request.setFile(new UploadedFile(content));
        return request;
    }

    private static KeyExportRequestDto exportRequest(String passphrase) {
        KeyExportRequestDto request = new KeyExportRequestDto();
        request.setPassphrase(new Passphrase(passphrase.toCharArray()));
        return request;
    }
}
