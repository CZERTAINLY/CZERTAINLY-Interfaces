package com.otilm.api.interfaces.connector.discovery.v2;

import com.otilm.api.model.common.error.ErrorCode;
import com.otilm.api.model.common.error.ProblemDetailExtended;
import com.otilm.api.testsupport.OpenApiProseAssertions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import static com.otilm.api.testsupport.OpenApiProseAssertions.assertNoJargon;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guards the public OpenAPI surface of {@link DiscoveryMetadataController}: every operation is
 * documented, every non-2xx response (should one ever be added at method level) names a real
 * {@link ErrorCode} and carries the {@link ProblemDetailExtended} content schema, no
 * Core-internal design jargon leaks into the published prose (summaries, descriptions, response
 * text, and parameter text alike; see {@link OpenApiProseAssertions} for the shared banned-term
 * list), and the controller extends the NG (common.v2) auth base rather than any legacy one.
 */
class DiscoveryMetadataControllerDocTest {

    @Test
    void everyOperationIsDocumented() {
        Method[] methods = DiscoveryMetadataController.class.getDeclaredMethods();
        assertTrue(methods.length >= 3, "expected at least 3 endpoints, found " + methods.length);

        for (Method m : methods) {
            Operation op = m.getAnnotation(Operation.class);
            assertNotNull(op, "missing @Operation on " + m.getName());
            assertFalse(op.summary().isBlank(), "blank @Operation summary on " + m.getName());
            assertFalse(op.description().isBlank(), "blank @Operation description on " + m.getName());

            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            boolean hasSuccess = Arrays.stream(responses.value())
                    .anyMatch(r -> r.responseCode().startsWith("2"));
            assertTrue(hasSuccess, "no 2xx response documented on " + m.getName());
            for (ApiResponse r : responses.value()) {
                assertFalse(r.description().isBlank(), "blank response description on " + m.getName());
                assertNoJargon(m.getName() + " " + r.responseCode() + " response", r.description());
            }

            for (Parameter p : m.getParameters()) {
                io.swagger.v3.oas.annotations.Parameter param =
                        p.getAnnotation(io.swagger.v3.oas.annotations.Parameter.class);
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
    void controllerExtendsCommonV2AuthBase() {
        List<String> supers = Arrays.stream(DiscoveryMetadataController.class.getInterfaces())
                .map(Class::getName).toList();
        assertTrue(supers.contains(
                        "com.otilm.api.interfaces.connector.common.v2.AuthProtectedConnectorController"),
                "DiscoveryMetadataController must extend the common.v2 auth base, not a legacy one; found " + supers);
    }

    @Test
    void tagDescriptionIsDocumentedAndJargonFree() {
        Tag tag = DiscoveryMetadataController.class.getAnnotation(Tag.class);
        assertNotNull(tag, "missing @Tag on DiscoveryMetadataController");
        assertFalse(tag.description().isBlank(), "blank @Tag description");
        assertNoJargon("@Tag", tag.description());
    }

    /**
     * Every non-2xx response must name a real {@link ErrorCode} in its description. No method on
     * this interface currently declares one at the method level (the base's 401/404/500 cover
     * the surface), so this passes vacuously today; it guards against a future addition slipping
     * in unnamed.
     */
    @Test
    void every4xxOr5xxResponseNamesAKnownErrorCode() {
        for (Method m : DiscoveryMetadataController.class.getDeclaredMethods()) {
            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            for (ApiResponse r : responses.value()) {
                if (!isErrorStatus(r.responseCode())) {
                    continue;
                }
                assertTrue(Arrays.stream(ErrorCode.values()).anyMatch(c -> r.description().contains(c.name())),
                        "response " + r.responseCode() + " on " + m.getName() + " does not name a known ErrorCode");
            }
        }
    }

    /**
     * Every non-2xx response must declare the {@link ProblemDetailExtended} content schema
     * explicitly, for the same reason as the sibling check on
     * {@code DiscoveryOperationControllerDocTest}. Passes vacuously today; guards a future
     * method-level error response from shipping without one.
     */
    @Test
    void every4xxOr5xxResponseDeclaresProblemDetailContent() {
        for (Method m : DiscoveryMetadataController.class.getDeclaredMethods()) {
            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            for (ApiResponse r : responses.value()) {
                if (!isErrorStatus(r.responseCode())) {
                    continue;
                }
                Content[] content = r.content();
                boolean hasProblemDetail = Arrays.stream(content).anyMatch(c ->
                        c.mediaType().equals(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                                && c.schema().implementation() == ProblemDetailExtended.class);
                assertTrue(hasProblemDetail,
                        "response " + r.responseCode() + " on " + m.getName()
                                + " does not declare ProblemDetailExtended content");
            }
        }
    }

    private static boolean isErrorStatus(String responseCode) {
        return responseCode.startsWith("4") || responseCode.startsWith("5");
    }
}
