package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.core.settings.BrandingSettingsDto;
import com.otilm.api.model.core.settings.BrandingSettingsUpdateDto;
import jakarta.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the branding endpoints of {@link SettingController} against their annotation values rather than prose. The
 * frontend's generated client calls these paths and Core implements them, so a change to either has to fail a build.
 *
 * <p>
 * Only the branding members are asserted; the pre-existing settings endpoints are left alone so that an unrelated
 * addition to the controller does not fail this test.
 */
class SettingControllerBrandingContractTest {

    private Method method(String name) {
        return Arrays
                .stream(SettingController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("SettingController declares no method named " + name));
    }

    @Test
    void theReadIsAGetUnderThePlatformSettingsPath() {
        Method read = method("getBrandingSettings");

        GetMapping mapping = read.getAnnotation(GetMapping.class);
        assertNotNull(mapping, "branding read must be a GET");
        assertArrayEquals(new String[]{"/platform/branding"}, mapping.path());
        assertArrayEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, mapping.produces());
        assertEquals(BrandingSettingsDto.class, read.getReturnType());
        assertEquals(0, read.getParameterCount());
    }

    @Test
    void theWriteIsAPutUnderThePlatformSettingsPath() {
        Method write = method("updateBrandingSettings");

        PutMapping mapping = write.getAnnotation(PutMapping.class);
        assertNotNull(mapping, "branding write must be a PUT");
        assertArrayEquals(new String[]{"/platform/branding"}, mapping.path());
        assertArrayEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, mapping.consumes());
        assertEquals(void.class, write.getReturnType());
    }

    /**
     * The DTO carries every colour and logo constraint, so dropping {@code @Valid} would let a malformed value reach
     * the settings table and, from there, every page render.
     */
    @Test
    void theWriteBodyIsValidated() {
        Parameter body = method("updateBrandingSettings").getParameters()[0];

        assertEquals(BrandingSettingsUpdateDto.class, body.getType());
        assertNotNull(body.getAnnotation(RequestBody.class), "branding body must be bound with @RequestBody");
        assertNotNull(body.getAnnotation(Valid.class), "branding body must be validated");
    }

    /**
     * The whole reason branding has its own endpoint: authorization is applied per method, so the write must not also
     * be reachable through the platform settings update, which is gated by the broader {@code UPDATE} action.
     */
    @Test
    void brandingHasExactlyOneWritePath() {
        assertTrue(Arrays
                .stream(SettingController.class.getDeclaredMethods())
                .filter(m -> Arrays.stream(m.getParameterTypes()).anyMatch(BrandingSettingsUpdateDto.class::equals))
                .allMatch(m -> m.getName().equals("updateBrandingSettings")),
                "only updateBrandingSettings may accept a branding update body");
    }
}
