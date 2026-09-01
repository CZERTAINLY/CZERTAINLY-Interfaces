package com.otilm.api.model.core.settings;

import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandingSettingsUpdateDtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    private static final String PNG_LOGO = "data:image/png;base64,iVBORw0KGgo=";
    private static final String SVG_LOGO = "data:image/svg+xml;base64,PHN2Zy8+";

    private static Stream<BiConsumer<BrandingSettingsUpdateDto, String>> colorSetters() {
        return Stream
                .of(BrandingSettingsUpdateDto::setPrimaryColor, BrandingSettingsUpdateDto::setSecondaryColor,
                        BrandingSettingsUpdateDto::setBackgroundColor, BrandingSettingsUpdateDto::setTextColor);
    }

    private static Stream<BiConsumer<BrandingSettingsUpdateDto, String>> logoSetters() {
        return Stream.of(BrandingSettingsUpdateDto::setLightLogo, BrandingSettingsUpdateDto::setDarkLogo);
    }

    /** Branding is entirely optional, so an operator who has configured none of it must not fail validation. */
    @Test
    void anEmptyUpdateIsValid() {
        assertTrue(VALIDATOR.validate(new BrandingSettingsUpdateDto()).isEmpty());
    }

    @Test
    void aFullyPopulatedUpdateIsValid() {
        BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
        dto.setPrimaryColor("#0073CF");
        dto.setSecondaryColor("#00a3e0");
        dto.setBackgroundColor("#FFFFFF");
        dto.setTextColor("#171717");
        dto.setLightLogo(PNG_LOGO);
        dto.setDarkLogo(SVG_LOGO);
        dto.setDefaultTheme(BrandingTheme.DARK);

        assertTrue(VALIDATOR.validate(dto).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("colorSetters")
    void everyColorAcceptsSixDigitHexInEitherCase(BiConsumer<BrandingSettingsUpdateDto, String> setter) {
        BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
        setter.accept(dto, "#aB12Ef");

        assertTrue(VALIDATOR.validate(dto).isEmpty());
    }

    /**
     * Three-digit shorthand, a missing hash and a bare name are all things an operator plausibly types, and each would
     * otherwise reach the stylesheet as a value the browser silently drops.
     */
    @ParameterizedTest
    @ValueSource(strings = {"#FFF", "0073CF", "blue", "#0073CFF", "#00 3CF", "rgb(0,115,207)", ""})
    void everyColorRejectsAnythingOtherThanSixDigitHex(String candidate) {
        colorSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, candidate);

            assertFalse(VALIDATOR.validate(dto).isEmpty(), "accepted invalid color " + candidate);
        });
    }

    @Test
    void eachColorIsRejectedIndependently() {
        BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
        colorSetters().forEach(setter -> setter.accept(dto, "nonsense"));

        // Derived rather than written out, so adding or removing a colour cannot leave this passing by coincidence.
        assertEquals(colorSetters().count(), VALIDATOR.validate(dto).size());
    }

    @ParameterizedTest
    @ValueSource(strings = {PNG_LOGO, SVG_LOGO})
    void bothLogoSlotsAcceptPngAndSvgDataUris(String logo) {
        logoSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, logo);

            assertTrue(VALIDATOR.validate(dto).isEmpty(), "rejected valid logo " + logo);
        });
    }

    /**
     * The media types are an allow-list rather than a deny-list, so the interesting cases are the ones that look close
     * enough to pass a naive check: another image type, a bare base64 payload with no media type, and an HTML document
     * dressed as a data URI.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "data:image/jpeg;base64,/9j/4AAQ",
            "data:text/html;base64,PGgxPmhpPC9oMT4=",
            "data:image/svg+xml,<svg/>",
            "iVBORw0KGgo=",
            "https://example.com/logo.png",
            "data:image/png;base64,"})
    void bothLogoSlotsRejectAnythingOtherThanAPngOrSvgDataUri(String candidate) {
        logoSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, candidate);

            assertFalse(VALIDATOR.validate(dto).isEmpty(), "accepted invalid logo " + candidate);
        });
    }

    /**
     * Base64 encodes three bytes as four characters, so a payload whose length is not a multiple of four, or whose
     * padding falls anywhere but the end of the final quartet, cannot be decoded at all. Each of these is accepted by a
     * naive "base64 characters followed by optional padding" pattern and then throws out of
     * {@link Base64.Decoder#decode(String)} — which is the wrong place to find out. The decoder is asserted here as
     * well so that a sample cannot quietly stop being a malformed one.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "data:image/png;base64,A",
            "data:image/png;base64,AAAAA",
            "data:image/png;base64,A=",
            "data:image/png;base64,AA=",
            "data:image/png;base64,A===",
            "data:image/png;base64,AAA==",
            "data:image/png;base64,AAAA=",
            "data:image/png;base64,=",
            "data:image/png;base64,==",
            "data:image/png;base64,A=AA",
            "data:image/png;base64,AA==AAAA"})
    void bothLogoSlotsRejectBase64ThatCannotBeDecoded(String candidate) {
        String payload = candidate.substring(candidate.indexOf(',') + 1);
        assertThrows(IllegalArgumentException.class, () -> Base64.getDecoder().decode(payload),
                "sample decodes, so it is not a malformed-base64 case: " + candidate);

        logoSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, candidate);

            assertFalse(VALIDATOR.validate(dto).isEmpty(), "accepted undecodable logo " + candidate);
        });
    }

    /** The other half of the same rule: every well-formed quartet count, padded or not, is still accepted. */
    @ParameterizedTest
    @ValueSource(strings = {
            "data:image/png;base64,AAAA",
            "data:image/png;base64,AAAAAAAA",
            "data:image/png;base64,AAA=",
            "data:image/png;base64,AA==",
            "data:image/png;base64,AAAAAAA=",
            "data:image/png;base64,AAAAAA==",
            "data:image/png;base64,AB+/"})
    void bothLogoSlotsAcceptEveryWellFormedQuartetCount(String candidate) {
        String payload = candidate.substring(candidate.indexOf(',') + 1);
        assertDoesNotThrow(() -> Base64.getDecoder().decode(payload),
                "sample does not decode, so it is not a well-formed case: " + candidate);

        logoSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, candidate);

            assertTrue(VALIDATOR.validate(dto).isEmpty(), "rejected decodable logo " + candidate);
        });
    }

    /**
     * {@link Base64.Decoder} tolerates a final quartet left unpadded, but the contract does not: padding is required by
     * RFC 4648, every encoder a browser or a client library reaches for emits it, and accepting both forms would mean
     * the same logo has two representations for Core to store and compare. Stricter than the decoder is the safe
     * direction — the contract can only refuse payloads Core would have accepted, never the reverse.
     */
    @ParameterizedTest
    @ValueSource(strings = {"data:image/png;base64,AA", "data:image/png;base64,AAA"})
    void bothLogoSlotsRejectAnUnpaddedFinalQuartetEvenThoughTheDecoderAcceptsIt(String candidate) {
        String payload = candidate.substring(candidate.indexOf(',') + 1);
        assertDoesNotThrow(() -> Base64.getDecoder().decode(payload));

        logoSetters().forEach(setter -> {
            BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
            setter.accept(dto, candidate);

            assertFalse(VALIDATOR.validate(dto).isEmpty(), "accepted unpadded logo " + candidate);
        });
    }

    /**
     * Logo image data is limited to one mebibyte. Base64 inflates it by a third, so the bound applied to the encoded
     * form has to be derived from the decoded one rather than guessed, or a logo inside the one-mebibyte limit is
     * refused by the contract.
     */
    @Test
    void theEncodedBoundLeavesRoomForAMegabyteOfImageData() {
        int encoded = 4 * ((BrandingSettingsUpdateDto.LOGO_MAX_DECODED_BYTES + 2) / 3);

        assertTrue(BrandingSettingsUpdateDto.LOGO_MAX_LENGTH > encoded,
                "encoded bound must exceed the base64 expansion of the decoded bound plus the data URI prefix");
        assertEquals(1024 * 1024, BrandingSettingsUpdateDto.LOGO_MAX_DECODED_BYTES);
    }

    @Test
    void logosAreBoundedInSize() {
        String oversized = "data:image/png;base64," + "A".repeat(BrandingSettingsUpdateDto.LOGO_MAX_LENGTH);

        BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
        dto.setLightLogo(oversized);

        Set<ConstraintViolation<BrandingSettingsUpdateDto>> violations = VALIDATOR.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("lightLogo", violations.iterator().next().getPropertyPath().toString());
    }

    /**
     * Branding is deliberately absent from the platform update body. Authorization is applied per controller method, so
     * a branding field here would be writable by anyone holding {@code SETTINGS} + {@code UPDATE} and the separate
     * {@code UPDATE_BRANDING} action would gate nothing. The write lives on its own endpoint instead.
     */
    @Test
    void isNotReachableThroughThePlatformSettingsUpdate() {
        assertTrue(
                Arrays
                        .stream(PlatformSettingsUpdateDto.class.getDeclaredFields())
                        .filter(field -> !field.isSynthetic())
                        .noneMatch(field -> BrandingSettingsUpdateDto.class.isAssignableFrom(field.getType())),
                "branding must not be writable through the UPDATE-gated platform settings body");
    }
}
