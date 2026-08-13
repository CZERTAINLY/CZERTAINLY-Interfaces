package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the three connector-scoped discovery relays on {@link DiscoveryController}.
 *
 * <p>
 * They are keyed by connector UUID while every other endpoint on the controller is keyed by run UUID, so the paths are
 * indistinguishable in shape and distinguished only by the placeholder name. That makes the path templates worth
 * pinning rather than reading: a relay that drifts to {@code /{uuid}/...} still compiles, still passes every other test
 * here, and starts answering on a path callers will read as run-scoped.
 *
 * <p>
 * Scoped to these three members only — the controller carries a long tail of existing endpoints in several annotation
 * styles, and sweeping them all would make this test fail on any unrelated addition.
 */
class DiscoveryControllerRelayContractTest {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{([^}]+)}");

    private Method method(String name) {
        return Arrays
                .stream(DiscoveryController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("DiscoveryController declares no method named " + name));
    }

    @ParameterizedTest
    @CsvSource({
            "listDiscoveryResources,/{connectorUuid}/resources",
            "getDiscoveryAttributes,/{connectorUuid}/attributes",
            "getDiscoveryResourceAttributes,/{connectorUuid}/{resource}/attributes"})
    void relayIsAGetOnItsDocumentedPath(String name, String expectedPath) {
        Method m = method(name);
        GetMapping mapping = m.getAnnotation(GetMapping.class);
        assertNotNull(mapping, "expected @GetMapping on " + name);
        assertEquals(1, mapping.path().length, "expected exactly one path on " + name);
        assertEquals(expectedPath, mapping.path()[0], "path mismatch on " + name);
    }

    /**
     * Spring resolves a {@code {placeholder}} to a {@code @PathVariable} by its explicit annotation value, or failing
     * that by the compiled parameter name — which only exists when javac ran with {@code -parameters}. A placeholder
     * matching neither fails when the endpoint is first called, not when it is built, and no other test here would
     * notice.
     */
    @ParameterizedTest
    @ValueSource(strings = {"listDiscoveryResources", "getDiscoveryAttributes", "getDiscoveryResourceAttributes"})
    void everyPathPlaceholderIsBoundToAParameter(String name) {
        Method m = method(name);
        List<String> bindable = new ArrayList<>();
        for (Parameter p : m.getParameters()) {
            PathVariable annotation = p.getAnnotation(PathVariable.class);
            if (annotation == null) {
                continue;
            }
            if (!annotation.value().isEmpty()) {
                bindable.add(annotation.value());
            } else if (p.isNamePresent()) {
                bindable.add(p.getName());
            }
        }

        Matcher placeholders = PLACEHOLDER.matcher(m.getAnnotation(GetMapping.class).path()[0]);
        while (placeholders.find()) {
            String placeholder = placeholders.group(1);
            assertTrue(bindable.contains(placeholder),
                    "path placeholder {" + placeholder + "} on " + name + " binds to no @PathVariable; resolvable "
                            + "names are " + bindable + " (an unnamed @PathVariable needs javac -parameters)");
        }
    }

    @Test
    void perResourceRelayTakesTheResourceAsAPathVariable() {
        Parameter resource = Arrays
                .stream(method("getDiscoveryResourceAttributes").getParameters())
                .filter(p -> p.getType() == Resource.class)
                .findFirst()
                .orElseThrow(() -> new AssertionError("getDiscoveryResourceAttributes takes no Resource parameter"));

        // Typed as Resource rather than String so the published contract enumerates the resource
        // codes instead of accepting any string; it binds by code, never by enum member name.
        assertNotNull(resource.getAnnotation(PathVariable.class),
                "the resource must be a @PathVariable — it is a path segment, not a query param");
    }

    /**
     * The two attribute relays exist only for Connectors implementing discovery v2, so each publishes the 422 a v1-only
     * Connector answers. The resource listing never refuses: Core synthesizes the certificates entry for a v1
     * Connector, so a documented 422 there would publish a refusal no implementation produces.
     */
    @Test
    void attributeRelaysPublishTheV1RefusalAndTheListingDoesNot() {
        assertTrue(documentedCodes("getDiscoveryAttributes").contains("422"),
                "getDiscoveryAttributes must document 422 for a v1-only Connector");
        assertTrue(documentedCodes("getDiscoveryResourceAttributes").contains("422"),
                "getDiscoveryResourceAttributes must document 422 for a v1-only Connector");
        assertFalse(documentedCodes("listDiscoveryResources").contains("422"),
                "listDiscoveryResources cannot refuse — Core synthesizes the v1 entry instead");
    }

    private Set<String> documentedCodes(String name) {
        ApiResponses responses = method(name).getAnnotation(ApiResponses.class);
        assertNotNull(responses, "missing @ApiResponses on " + name);
        return Arrays.stream(responses.value()).map(ApiResponse::responseCode).collect(Collectors.toSet());
    }

    /**
     * The relays must not sit on {@code ConnectorController}: its attribute endpoints are generic across function
     * groups, and a provider-interface-specific relay there would be the odd one out.
     */
    @Test
    void relaysDoNotLingerOnTheConnectorController() {
        List<String> strays = Arrays
                .stream(ConnectorController.class.getDeclaredMethods())
                .map(Method::getName)
                .filter(name -> name.equals("listDiscoveryResources") || name.equals("getDiscoveryAttributes")
                        || name.equals("getDiscoveryResourceAttributes"))
                .toList();

        assertEquals(List.of(), strays,
                "discovery-specific relays belong on DiscoveryController, not ConnectorController");
    }
}
