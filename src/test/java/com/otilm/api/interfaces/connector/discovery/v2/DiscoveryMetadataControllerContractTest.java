package com.otilm.api.interfaces.connector.discovery.v2;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pins the wire shape of {@link DiscoveryMetadataController} — base path, per-method path, and the documented 2xx code
 * matching the actual (or default) {@code @ResponseStatus} — against annotation values rather than prose, so a typo in
 * a mapping path or a mismatched status code fails a build instead of shipping into the generated document.
 */
class DiscoveryMetadataControllerContractTest {

    private static final Map<String, String> EXPECTED_PATHS = Map
            .of("listSupportedResources", "/resources", "listRunAttributes", "/attributes", "listResourceAttributes",
                    "/{resource}/attributes");

    @Test
    void basePathMatchesDesign() {
        RequestMapping mapping = DiscoveryMetadataController.class.getAnnotation(RequestMapping.class);
        assertNotNull(mapping, "missing @RequestMapping on DiscoveryMetadataController");
        assertEquals(1, mapping.value().length, "expected exactly one base path");
        assertEquals("/v2/discoveryProvider", mapping.value()[0]);
    }

    @Test
    void everyMethodMapsToItsExpectedPath() {
        for (Method m : DiscoveryMetadataController.class.getDeclaredMethods()) {
            GetMapping gm = m.getAnnotation(GetMapping.class);
            assertNotNull(gm, "expected @GetMapping on " + m.getName());
            assertEquals(1, gm.path().length, "expected exactly one path on " + m.getName());
            assertEquals(EXPECTED_PATHS.get(m.getName()), gm.path()[0], "path mismatch on " + m.getName());
        }
    }

    @Test
    void responseStatusMatchesDocumented2xxCode() {
        for (Method m : DiscoveryMetadataController.class.getDeclaredMethods()) {
            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            String documented2xx = Arrays
                    .stream(responses.value())
                    .map(ApiResponse::responseCode)
                    .filter(c -> c.startsWith("2"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("no 2xx documented on " + m.getName()));

            ResponseStatus rs = m.getAnnotation(ResponseStatus.class);
            int actualStatus = rs != null ? rs.value().value() : HttpStatus.OK.value();
            assertEquals(Integer.parseInt(documented2xx), actualStatus, "documented 2xx (" + documented2xx
                    + ") does not match actual @ResponseStatus/default on " + m.getName());
        }
    }
}
