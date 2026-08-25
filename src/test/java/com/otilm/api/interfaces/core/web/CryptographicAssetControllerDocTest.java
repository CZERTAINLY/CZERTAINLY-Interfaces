package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.common.ErrorMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.otilm.api.testsupport.OpenApiProseAssertions.assertNoJargon;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the public OpenAPI surface of {@link CryptographicAssetController}: every operation carries a non-blank
 * summary and description, no internal design jargon leaks into the published prose, and every method-level error
 * response declares the legacy {@link ErrorMessageDto} content schema this controller deliberately uses.
 */
class CryptographicAssetControllerDocTest {

    @Test
    void everyOperationIsDocumented() {
        Method[] methods = CryptographicAssetController.class.getDeclaredMethods();
        assertTrue(methods.length >= 3, "expected at least 3 endpoints, found " + methods.length);

        for (Method m : methods) {
            Operation op = m.getAnnotation(Operation.class);
            assertNotNull(op, "missing @Operation on " + m.getName());
            assertFalse(op.summary().isBlank(), "blank @Operation summary on " + m.getName());
            assertFalse(op.description().isBlank(), "blank @Operation description on " + m.getName());

            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            boolean hasSuccess = Arrays.stream(responses.value()).anyMatch(r -> r.responseCode().startsWith("2"));
            assertTrue(hasSuccess, "no 2xx response documented on " + m.getName());
            for (ApiResponse r : responses.value()) {
                assertFalse(r.description().isBlank(), "blank response description on " + m.getName());
                assertNoJargon(m.getName() + " " + r.responseCode() + " response", r.description());
            }

            for (Parameter p : m.getParameters()) {
                // Fully qualified deliberately: Parameter is already java.lang.reflect.Parameter here, the
                // reflected method parameter this loop walks. Do not "clean up" into an import - it would shadow
                // the reflection type this file depends on.
                io.swagger.v3.oas.annotations.Parameter param = p
                        .getAnnotation(io.swagger.v3.oas.annotations.Parameter.class);
                if (param != null) {
                    assertFalse(param.description().isBlank(), "blank @Parameter description on " + m.getName());
                    assertNoJargon(m.getName() + " parameter", param.description());
                }
            }

            assertNoJargon(m.getName(), op.summary());
            assertNoJargon(m.getName(), op.description());
        }
    }

    @Test
    void tagDescriptionIsDocumentedAndJargonFree() {
        Tag tag = CryptographicAssetController.class.getAnnotation(Tag.class);
        assertNotNull(tag, "missing @Tag on CryptographicAssetController");
        assertFalse(tag.description().isBlank(), "blank @Tag description");
        assertNoJargon("@Tag", tag.description());
    }

    @Test
    void controllerExtendsTheAuthProtectedBase() {
        List<String> supers = Arrays
                .stream(CryptographicAssetController.class.getInterfaces())
                .map(Class::getName)
                .toList();
        assertTrue(supers.contains("com.otilm.api.interfaces.AuthProtectedController"),
                "CryptographicAssetController must extend AuthProtectedController; found " + supers);
    }

    /**
     * Error responses on this controller deliberately use the legacy {@link ErrorMessageDto} model to match the rest of
     * the core web surface; this pins that every method-level error response actually declares it. The one exception is
     * 422, where the platform's list endpoints share a string-array validation shape (the same one every other list
     * controller declares), so a 422 may carry that instead.
     */
    @Test
    void every4xxOr5xxResponseDeclaresErrorMessageContent() {
        for (Method m : CryptographicAssetController.class.getDeclaredMethods()) {
            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            for (ApiResponse r : responses.value()) {
                if (!(r.responseCode().startsWith("4") || r.responseCode().startsWith("5"))) {
                    continue;
                }
                Content[] content = r.content();
                boolean hasErrorMessage = Arrays
                        .stream(content)
                        .anyMatch(c -> c.schema().implementation() == ErrorMessageDto.class
                                && (c.mediaType().isEmpty() || c.mediaType().equals(MediaType.APPLICATION_JSON_VALUE)));
                boolean hasCanonicalValidationArray = r.responseCode().equals("422")
                        && Arrays.stream(content).anyMatch(c -> c.array().schema().implementation() == String.class);
                assertTrue(hasErrorMessage || hasCanonicalValidationArray, "response " + r.responseCode() + " on "
                        + m.getName() + " declares neither ErrorMessageDto nor the canonical 422 validation content");
            }
        }
    }
}
