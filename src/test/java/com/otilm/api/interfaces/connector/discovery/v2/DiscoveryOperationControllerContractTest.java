package com.otilm.api.interfaces.connector.discovery.v2;

import com.otilm.api.model.connector.discovery.v2.DiscoveryStreamRequestDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pins the wire shape of {@link DiscoveryOperationController} — base path, per-method path, the
 * documented 2xx code matching the actual (or default) {@code @ResponseStatus}, and the NDJSON
 * media type on {@code stream} — against annotation values rather than prose. A typo in a
 * mapping path, or {@code @ApiResponse(responseCode = "202")} paired with a contradicting
 * {@code @ResponseStatus}, fails a build instead of shipping a document the implementation
 * contradicts.
 */
class DiscoveryOperationControllerContractTest {

    private static final Map<String, String> EXPECTED_PATHS = Map.of(
            "initiate", "/initiate",
            "status", "/status",
            "results", "/results",
            "stream", "/stream",
            "stop", "/stop",
            "resume", "/resume",
            "cancel", "/cancel"
    );

    @Test
    void basePathMatchesDesign() {
        RequestMapping mapping = DiscoveryOperationController.class.getAnnotation(RequestMapping.class);
        assertNotNull(mapping, "missing @RequestMapping on DiscoveryOperationController");
        assertEquals(1, mapping.value().length, "expected exactly one base path");
        assertEquals("/v2/discoveryProvider/discoveries", mapping.value()[0]);
    }

    @Test
    void everyMethodMapsToItsExpectedPath() {
        for (Method m : DiscoveryOperationController.class.getDeclaredMethods()) {
            PostMapping pm = m.getAnnotation(PostMapping.class);
            assertNotNull(pm, "expected @PostMapping on " + m.getName());
            assertEquals(1, pm.path().length, "expected exactly one path on " + m.getName());
            assertEquals(EXPECTED_PATHS.get(m.getName()), pm.path()[0], "path mismatch on " + m.getName());
        }
    }

    @Test
    void responseStatusMatchesDocumented2xxCode() {
        for (Method m : DiscoveryOperationController.class.getDeclaredMethods()) {
            ApiResponses responses = m.getAnnotation(ApiResponses.class);
            assertNotNull(responses, "missing @ApiResponses on " + m.getName());
            String documented2xx = Arrays.stream(responses.value())
                    .map(ApiResponse::responseCode)
                    .filter(c -> c.startsWith("2"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("no 2xx documented on " + m.getName()));

            ResponseStatus rs = m.getAnnotation(ResponseStatus.class);
            int actualStatus = rs != null ? rs.value().value() : HttpStatus.OK.value();
            assertEquals(Integer.parseInt(documented2xx), actualStatus,
                    "documented 2xx (" + documented2xx + ") does not match actual @ResponseStatus/default on " + m.getName());
        }
    }

    @Test
    void streamProducesNdjson() throws NoSuchMethodException {
        Method stream = DiscoveryOperationController.class.getDeclaredMethod("stream",
                DiscoveryStreamRequestDto.class);
        PostMapping pm = stream.getAnnotation(PostMapping.class);
        assertArrayEquals(new String[]{MediaType.APPLICATION_NDJSON_VALUE}, pm.produces(),
                "stream must produce exactly application/x-ndjson");
    }
}
