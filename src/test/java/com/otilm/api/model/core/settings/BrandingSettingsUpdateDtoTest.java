package com.otilm.api.model.core.settings;

import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
                        BrandingSettingsUpdateDto::setTertiaryColor, BrandingSettingsUpdateDto::setBackgroundColor,
                        BrandingSettingsUpdateDto::setTextColor);
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
        dto.setTertiaryColor("#7B61FF");
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

        assertEquals(5, VALIDATOR.validate(dto).size());
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

    @Test
    void logosAreBoundedInSize() {
        String oversized = "data:image/png;base64," + "A".repeat(BrandingSettingsUpdateDto.LOGO_MAX_LENGTH);

        BrandingSettingsUpdateDto dto = new BrandingSettingsUpdateDto();
        dto.setLightLogo(oversized);

        Set<ConstraintViolation<BrandingSettingsUpdateDto>> violations = VALIDATOR.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("lightLogo", violations.iterator().next().getPropertyPath().toString());
    }

    /** Without the {@code @Valid} on the parent field, a malformed color would be persisted unchecked. */
    @Test
    void cascadesFromThePlatformSettingsUpdate() {
        BrandingSettingsUpdateDto branding = new BrandingSettingsUpdateDto();
        branding.setPrimaryColor("not-a-color");

        PlatformSettingsUpdateDto parent = new PlatformSettingsUpdateDto();
        parent.setBranding(branding);

        assertFalse(VALIDATOR.validate(parent).isEmpty(), "@Valid must cascade into the branding sub-DTO");
    }
}
